package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.osgi.framework.debug.Debug;
import org.eclipse.osgi.util.NLS;

public class NLSUtils extends NLS{
	private static final String IGNORE = "ignore"; //$NON-NLS-1$
	private static final String EXTENSION = ".properties"; //$NON-NLS-1$
	static final Object ASSIGNED = new Object();
	static final int SEVERITY_ERROR = 0x04;
	static final int SEVERITY_WARNING = 0x02;
	
	static void load(String bundleName, Class<?> clazz){
		bundleName += EXTENSION;
		final Field[] fieldArray = clazz.getDeclaredFields();

		boolean isAccessible = (clazz.getModifiers() & Modifier.PUBLIC) != 0;

		//build a map of field names to Field objects
		final int len = fieldArray.length;
		Map<Object, Object> fields = new HashMap<Object, Object>(len * 2);
		for (int i = 0; i < len; i++)
			fields.put(fieldArray[i].getName(), fieldArray[i]);
		InputStream input = null;
		try {
			input = new FileInputStream(bundleName);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			final MessagesProperties properties = new MessagesProperties(fields, bundleName, isAccessible);
			properties.load(input);
		} catch (IOException e) {
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					// ignore
				}
		}
	}
	private static class MessagesProperties extends Properties {

		private static final int MOD_EXPECTED = Modifier.PUBLIC | Modifier.STATIC;
		private static final int MOD_MASK = MOD_EXPECTED | Modifier.FINAL;
		private static final long serialVersionUID = 1L;

		private final String bundleName;
		private final Map<Object, Object> fields;
		private final boolean isAccessible;

		public MessagesProperties(Map<Object, Object> fieldMap, String bundleName, boolean isAccessible) {
			super();
			this.fields = fieldMap;
			this.bundleName = bundleName;
			this.isAccessible = isAccessible;
		}

		/* (non-Javadoc)
		 * @see java.util.Hashtable#put(java.lang.Object, java.lang.Object)
		 */
		public synchronized Object put(Object key, Object value) {
			Object fieldObject = fields.put(key, ASSIGNED);
			// if already assigned, there is nothing to do
			if (fieldObject == ASSIGNED)
				return null;
			if (fieldObject == null) {
				final String msg = "NLS unused message: " + key + " in: " + bundleName;//$NON-NLS-1$ //$NON-NLS-2$
				if (Debug.DEBUG_MESSAGE_BUNDLES)
					System.out.println(msg);
				return null;
			}
			final Field field = (Field) fieldObject;
			//can only set value of public static non-final fields
			if ((field.getModifiers() & MOD_MASK) != MOD_EXPECTED)
				return null;
			try {
				// Check to see if we are allowed to modify the field. If we aren't (for instance 
				// if the class is not public) then change the accessible attribute of the field
				// before trying to set the value.
				if (!isAccessible)
					field.setAccessible(true);
				// Set the value into the field. We should never get an exception here because
				// we know we have a public static non-final field. If we do get an exception, silently
				// log it and continue. This means that the field will (most likely) be un-initialized and
				// will fail later in the code and if so then we will see both the NPE and this error.

				// Extra care is taken to be sure we create a String with its own backing char[] (bug 287183)
				// This is to ensure we do not keep the key chars in memory.
				field.set(null, new String(((String) value).toCharArray()));
			} catch (Exception e) {
			}
			return null;
		}
	}
}
