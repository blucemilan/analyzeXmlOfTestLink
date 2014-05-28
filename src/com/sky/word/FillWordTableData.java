package com.sky.word;

import java.math.BigInteger;
import java.util.List;

import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Br;
import org.docx4j.wml.CTVerticalJc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.STHint;
import org.docx4j.wml.STVerticalJc;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;

import com.sky.bo.TestCase;
import com.sky.bo.TestStep;
import com.sky.bo.TestSuite;

public class FillWordTableData {
	/**
	 * 功能描述：填充表格内容
	 * 
	 * @param wordPackage 文档处理包对象
	 * @param tbl 表格对象
	 * @param cases 表格数据
	 * @param isFixedTitle 是否固定表头
	 * @param tFontFamily 表头字体
	 * @param tFontSize 表头字体大小
	 * @param tIsBlod 表头是否加粗
	 * @param tJcEnumeration 表头对齐方式
	 * @param fontFamily 表格字体
	 * @param fontSize 表格字号
	 * @param isBlod 表格内容是否加粗
	 * @param jcEnumeration 表格对齐方式
	 */
	@SuppressWarnings("unused")
	public static void fillTableData(Tbl tbl, TestCase cases, boolean isFixedTitle, String tFontFamily,
			String tFontSize, boolean tIsBlod, JcEnumeration tJcEnumeration, String fontFamily, String fontSize, boolean isBlod,
			JcEnumeration jcEnumeration) {
		List rowList = tbl.getContent();
		// 整个表格的行数
		//int rows = rowList.size();
		//第一行第一列
		Tr tr0 = (Tr) XmlUtils.unwrap(rowList.get(0));
		Tc tc0_0 = (Tc) XmlUtils.unwrap(tr0.getContent().get(0));
		fillCellData(tc0_0, "用例编号及名称", tFontFamily, tFontSize, tIsBlod, jcEnumeration);
		//第一行第二列
		Tc tc0_1 = (Tc) XmlUtils.unwrap(tr0.getContent().get(1));
		fillCellData(tc0_1, cases.getNumber() + "(" + cases.getName() + ")", tFontFamily, tFontSize, isBlod, jcEnumeration);
		//第二行第一列
		Tr tr1 = (Tr) XmlUtils.unwrap(rowList.get(1));
		Tc tc1_0 = (Tc) XmlUtils.unwrap(tr1.getContent().get(0));
		fillCellData(tc1_0, "测试目标", tFontFamily, tFontSize, tIsBlod, jcEnumeration);
		//第二行第二列
		Tc tc1_1 = (Tc) XmlUtils.unwrap(tr1.getContent().get(1));
		fillCellData(tc1_1, cases.getGoal(), tFontFamily, tFontSize, isBlod, jcEnumeration);
		//第三行第一列
		Tr tr2 = (Tr) XmlUtils.unwrap(rowList.get(2));
		Tc tc2_0 = (Tc) XmlUtils.unwrap(tr2.getContent().get(0));
		fillCellData(tc2_0, "前置条件", tFontFamily, tFontSize, tIsBlod, jcEnumeration);
		//第三行第二列
		Tc tc2_1 = (Tc) XmlUtils.unwrap(tr2.getContent().get(1));
		fillCellData(tc2_1, cases.getPreconditions(), tFontFamily, tFontSize, isBlod, jcEnumeration);
		//第四行第一列
		Tr tr3 = (Tr) XmlUtils.unwrap(rowList.get(3));
		Tc tc3_0 = (Tc) XmlUtils.unwrap(tr3.getContent().get(0));
		fillCellData(tc3_0, "参考需求", tFontFamily, tFontSize, tIsBlod, jcEnumeration);
		//第四行第二列
		Tc tc3_1 = (Tc) XmlUtils.unwrap(tr3.getContent().get(1));
		fillCellData(tc3_1, cases.getRefRequirement(), tFontFamily, tFontSize, isBlod, jcEnumeration);	
		//第五行第一列
		Tr tr4 = (Tr) XmlUtils.unwrap(rowList.get(4));
		Tc tc4_0 = (Tc) XmlUtils.unwrap(tr4.getContent().get(0));
		fillCellData(tc4_0, "步骤", tFontFamily, tFontSize, tIsBlod, tJcEnumeration);
		//第五行第二列
		Tc tc4_1 = (Tc) XmlUtils.unwrap(tr4.getContent().get(1));
		fillCellData(tc4_1, "操作", tFontFamily, tFontSize, tIsBlod, tJcEnumeration);
		//第五行第三列
		Tc tc4_2 = (Tc) XmlUtils.unwrap(tr4.getContent().get(2));
		fillCellData(tc4_2, "期望结果", tFontFamily, tFontSize, tIsBlod, tJcEnumeration);
		List<TestStep> stepsList = cases.getTestStepList();
		int stepNum = 1;
		for(TestStep steps : stepsList){
			Tr tr = (Tr) XmlUtils.unwrap(rowList.get(4 + stepNum));
			Tc tc = (Tc) XmlUtils.unwrap(tr.getContent().get(0));
			fillCellData(tc, steps.getStep_number(), fontFamily, fontSize, isBlod, jcEnumeration);
			tc = (Tc) XmlUtils.unwrap(tr.getContent().get(1));
			fillCellData(tc, steps.getActions(), fontFamily, fontSize, isBlod, jcEnumeration);
			tc = (Tc) XmlUtils.unwrap(tr.getContent().get(2));
			fillCellData(tc, steps.getExpectedResults(), fontFamily, fontSize, isBlod, jcEnumeration);
			stepNum++;
		}		
	}

	/**
	 * 功能描述：填充单元格内容
	 * 
	 * @param tc 单元格对象
	 * @param data 内容
	 * @param fontFamily 字体
	 * @param fontSize 字号
	 * @param isBlod 是否加粗
	 * @param jcEnumeration 对齐方式
	 */
	private static void fillCellData(Tc tc, String data, String fontFamily, String fontSize, boolean isBlod, JcEnumeration jcEnumeration) {
		ObjectFactory factory = Context.getWmlObjectFactory();
		if(null!=data&&!"".equals(data)){
			String[] datalist = data.trim().split("\r");
			for(int i=0;i<datalist.length;i++){
				
				fillCellData(tc, datalist[i], fontFamily, fontSize, isBlod, jcEnumeration, factory, i);
			}
		}
		
	}
	@SuppressWarnings("unused")
	private static void fillCellData(Tc tc, String data, String fontFamily, String fontSize, boolean isBlod, 
			JcEnumeration jcEnumeration, ObjectFactory factory, int i) {
		P p = null;
		if(i==0){
			p = (P) XmlUtils.unwrap(tc.getContent().get(0));
		}else{
			p = factory.createP();
		}
		// 设置表格内容的对齐方式
		setCellContentStyle(p, jcEnumeration);
		org.docx4j.wml.Text t = factory.createText();
		t.setValue(data);
		org.docx4j.wml.R run = factory.createR();
		// 设置表格内容字体样式
		run.setRPr(getRPr(fontFamily, fontSize, isBlod));
		
		TcPr tcpr = tc.getTcPr();
		if (tcpr == null) {
			tcpr = factory.createTcPr();
		}
		// 设置内容垂直居中
		CTVerticalJc valign = factory.createCTVerticalJc();
		valign.setVal(STVerticalJc.CENTER);
		tcpr.setVAlign(valign);
		run.getContent().add(t);
		p.getContent().add(run);
		if(i > 0){
			tc.getContent().add(p);
		}
		
	}
	
	/**
	 * 功能描述：设置单元格内容对齐方式
	 * 
	 * @param p
	 *            内容
	 * @param jcEnumeration
	 *            对齐方式
	 * @author myclover
	 */
	private static void setCellContentStyle(P p, JcEnumeration jcEnumeration) {
		PPr pPr = p.getPPr();
		if (pPr == null) {
			ObjectFactory factory = Context.getWmlObjectFactory();
			pPr = factory.createPPr();
		}
		org.docx4j.wml.Jc jc = pPr.getJc();
		if (jc == null) {
			jc = new org.docx4j.wml.Jc();
		}
		jc.setVal(jcEnumeration);
		pPr.setJc(jc);
		p.setPPr(pPr);
	}
	private static RPr getRPr(String fontFamily , String hpsMeasureSize , boolean isBlod){  
        return getRPr(fontFamily, "000000", hpsMeasureSize, STHint.EAST_ASIA, isBlod);  
    } 
	/**
	 * 功能描述：设置字体的样式
	 * 
	 * @param fontFamily 字体类型
	 * @param colorVal 字体颜色
	 * @param hpsMeasureSize 字号大小
	 * @param sTHint 字体格式
	 * @param isBlod 是否加粗
	 * @return 返回值：返回字体样式对象
	 * @throws Exception
	 * @author myclover
	 */
	private static RPr getRPr(String fontFamily, String colorVal, String hpsMeasureSize, STHint sTHint, boolean isBlod) {
		ObjectFactory factory = Context.getWmlObjectFactory();
		RPr rPr = factory.createRPr();
		org.docx4j.wml.RFonts rf = new org.docx4j.wml.RFonts();
		rf.setHint(sTHint);
		rf.setAscii(fontFamily);
		rf.setHAnsi(fontFamily);
		rPr.setRFonts(rf);

		BooleanDefaultTrue bdt = Context.getWmlObjectFactory()
				.createBooleanDefaultTrue();
		rPr.setBCs(bdt);
		if (isBlod) {
			rPr.setB(bdt);
		}
		org.docx4j.wml.Color color = new org.docx4j.wml.Color();
		color.setVal(colorVal);
		rPr.setColor(color);

		org.docx4j.wml.HpsMeasure sz = new org.docx4j.wml.HpsMeasure();
		sz.setVal(new BigInteger(hpsMeasureSize));
		rPr.setSz(sz);
		rPr.setSzCs(sz);

		return rPr;
	}

	/**
	 * 功能描述：固定表头
	 * 
	 * @param tr
	 *            行对象
	 * @author myclover
	 */
	private static void fixedTitle(Tr tr) {
		ObjectFactory factory = Context.getWmlObjectFactory();
		BooleanDefaultTrue bdt = factory.createBooleanDefaultTrue();
		// 表示固定表头
		bdt.setVal(true);
		TrPr trpr = tr.getTrPr();
		if (trpr == null) {
			trpr = factory.createTrPr();
		}
		trpr.getCnfStyleOrDivIdOrGridBefore().add(
				factory.createCTTrPrBaseTblHeader(bdt));
		tr.setTrPr(trpr);
	}

	/**
	 * 功能描述：Object数据转换为String类型
	 * 
	 * @param obj
	 * @param defaultStr
	 *            如果obj对象为空，则返回的值
	 * @return
	 * @author myclover
	 */
	private static String converObjToStr(Object obj, String defaultStr) {
		if (obj != null) {
			return obj.toString();
		}
		return defaultStr;
	}
}
