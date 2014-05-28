package com.sky.word;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.model.structure.HeaderFooterPolicy;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;
import org.docx4j.wml.PPrBase.NumPr;
import org.docx4j.wml.PPrBase.OutlineLvl;
import org.docx4j.wml.PPrBase.NumPr.Ilvl;
import org.docx4j.wml.PPrBase.NumPr.NumId;

public class WordUitls {
	public static P createNumberedParagraph(long numId, long ilvl, String outlineLv, String paragraphText, ObjectFactory factory) {
		P p = factory.createP();
		Text t = factory.createText();
		t.setValue(paragraphText);
		R run = factory.createR();
		run.getRunContent().add(t);
		p.getParagraphContent().add(run);

		PPr ppr = factory.createPPr();
		PPrBase.PStyle style = factory.createPPrBasePStyle();
		style.setVal(outlineLv);
		ppr.setPStyle(style);
		/*OutlineLvl lv = factory.createPPrBaseOutlineLvl();
		lv.setVal(BigInteger.valueOf(outlineLv));
		ppr.setOutlineLvl(lv);*/
		
		p.setPPr(ppr);
		// Create and add <w:numPr>
		NumPr numPr = factory.createPPrBaseNumPr();
		ppr.setNumPr(numPr);
		// The <w:ilvl> element
		Ilvl ilvlElement = factory.createPPrBaseNumPrIlvl();
		numPr.setIlvl(ilvlElement);
		ilvlElement.setVal(BigInteger.valueOf(ilvl));
		// The <w:numId> element
		NumId numIdElement = factory.createPPrBaseNumPrNumId();
		numPr.setNumId(numIdElement);
		numIdElement.setVal(BigInteger.valueOf(numId));
		return p;
	}

	public static void rePlaceTxt(String oldTxt, String newTxt, MainDocumentPart part) {
		// 替换内容
		String xpath = "//w:t[contains(text(),'" + oldTxt + "')]";
		try {
			List<Object> list = part.getJAXBNodesViaXPath(xpath, true);
			for (Object obj : list) {
				Text text = (Text) ((JAXBElement) obj).getValue();
				text.setValue(text.getValue().replaceAll(oldTxt, newTxt));
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void rePlaceTxtInHead(String oldTxt, String newTxt, WordprocessingMLPackage wordMLPackage) {
		// 页眉
		List<SectionWrapper> sectionWrappers = wordMLPackage.getDocumentModel().getSections();
		for (SectionWrapper sw : sectionWrappers) {
			HeaderFooterPolicy hfp = sw.getHeaderFooterPolicy();
			HeaderPart header = hfp.getDefaultHeader();
			List<Object> list = header.getContent();
			for (Object obj : list) {	
				try{
					setHead((P)obj, oldTxt, newTxt);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	public static String setBlank(String newTxt){
		StringBuffer str = new StringBuffer();
		int num = 64 - newTxt.length()*2;
		for(int i=0;i<num;i++){
			str.append(" ");
		}
		return str.toString();
	}
	
	public static void setHead(P p, String oldTxt, String newTxt){
		List<Object> children = p.getParagraphContent();	
		for (Object o : children ) {
			R  run = (R)o;
	    	List runContent = run.getContent();
			for (Object o2 : runContent ) {					
				if ( o2 instanceof javax.xml.bind.JAXBElement) {
					if ( ((JAXBElement)o2).getDeclaredType().getName().equals("org.docx4j.wml.Text") ) {
						Text t = (Text)((JAXBElement)o2).getValue();
						if(null!=t.getValue()&&"".equals(t.getValue().trim())){
							t.setValue(setBlank(newTxt));
						}else if(oldTxt.equals(t.getValue())){
							t.setValue(newTxt);
						}
					}
				} else {
//				    	System.out.println(o2.getClass().getName());						
				}
			}
		} 
	}
	public static int[] getTableWidth(){
		int[] widths = new int[3]; 
		widths[0] = 1843;
		widths[1] = 3402;
		widths[2] = 3402;
		return widths;
	}
	
	public static String dealData(String content){
		if(null!=content&&!"".equals(content)){
			return content.replaceAll("\r", "\r\n");
		}
		return "";
	}
}
