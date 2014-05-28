package com.sky.word;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.wml.CTHeight;
import org.docx4j.wml.CTShd;
import org.docx4j.wml.CTTblLayoutType;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.STTblLayoutType;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblGrid;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;
import org.docx4j.wml.TcPrInner.HMerge;
import org.docx4j.wml.TcPrInner.VMerge;

public class CreateTable {
	 /** 
     * 功能描述：创建文档表格，全部表格边框都为1 
     * @param rows    行数 
     * @param cols    列数 
     * @param widths  每列的宽度 
     * @return   返回值：返回表格对象 
     * @throws JAXBException 
     */  
    public static Tbl createBorderTable(int rows, int cols, int[] widths) throws JAXBException {
        ObjectFactory factory = Context.getWmlObjectFactory();  
        Tbl tbl = factory.createTbl();  
        // w:tblPr  
        StringBuffer tblSb = new StringBuffer();  
        tblSb.append("<w:tblPr ").append(Namespaces.W_NAMESPACE_DECLARATION).append(">");  
        tblSb.append("<w:tblStyle w:val=\"TableGrid\"/>");  
        tblSb.append("<w:tblW w:w=\"8647\" w:type=\"auto\"/><w:tblInd w:w=\"392\" w:type=\"dxa\"/>");  
        tblSb.append("<w:tblBorders><w:top w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"auto\"/>");  
        tblSb.append("<w:left w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"auto\"/>");  
        tblSb.append("<w:bottom w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"auto\"/>");  
        tblSb.append("<w:right w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"auto\"/>");  
        tblSb.append("</w:tblBorders>");  
        tblSb.append("<w:tblLook w:val=\"04A0\"/>");  
        tblSb.append("</w:tblPr>"); 
        TblPr tblPr = null;  
        try {  
            tblPr = (TblPr) XmlUtils.unmarshalString(tblSb.toString());  
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  
        tbl.setTblPr(tblPr);  
        if (tblPr != null) {  
            Jc jc = factory.createJc();  
            //单元格居中对齐  
            jc.setVal(JcEnumeration.LEFT);  
            tblPr.setJc(jc);  
            CTTblLayoutType tbll = factory.createCTTblLayoutType();  
            // 固定列宽  
            tbll.setType(STTblLayoutType.FIXED);  
            tblPr.setTblLayout(tbll);  
        }  
        // <w:tblGrid><w:gridCol w:w="4788"/>  
        TblGrid tblGrid = factory.createTblGrid();  
        tbl.setTblGrid(tblGrid);  
        // Add required <w:gridCol w:w="4788"/>  
        for (int i = 1; i <= cols; i++) {  
            TblGridCol gridCol = factory.createTblGridCol();  
            gridCol.setW(BigInteger.valueOf(widths[i - 1]));  
            tblGrid.getGridCol().add(gridCol);  
        }  
        // Now the rows  
        for (int j = 1; j <= rows; j++) {           
            Tr tr = createTr(factory);
            tbl.getContent().add(tr);  
            // The cells            
            for (int i = 1; i <= cols; i++) {  
            	createTc(factory, tr, widths[i - 1], j, i, getBgColor(i, j));
            }  
             
        }  
        return tbl;
    } 
    public static Tr createTr(ObjectFactory factory){
    	Tr tr = factory.createTr();  
        CTHeight  height = factory.createCTHeight();
        height.setVal(BigInteger.valueOf(454));
        TrPr trpr = factory.createTrPr();
        JAXBElement<CTHeight> hObj = factory.createCTTrPrBaseTrHeight(height);
        List<JAXBElement<?>> list = trpr.getCnfStyleOrDivIdOrGridBefore();
        list.add(hObj);
        tr.setTrPr(trpr);
        return tr;
    }
    public static void createTc(ObjectFactory factory, Tr tr, int width, int j, int i, String bgColor){
    	Tc tc = factory.createTc();     
        TcPr tcPr = factory.createTcPr();
        CTShd shd = factory.createCTShd();
        shd.setFill(bgColor);//C2D69B
        //shd.setFill("D9D9D9");//C2D69B
        tcPr.setShd(shd);
        tc.setTcPr(tcPr);
        TblWidth cellWidth = factory.createTblWidth();  
        tcPr.setTcW(cellWidth);  
        cellWidth.setType("dxa");  
        cellWidth.setW(BigInteger.valueOf(width));  
        tc.getContent().add(factory.createP());  
        if(j<=4){//前4行
        	setCellHMerge(tc, j-1, j-1, i - 1, 1, 2);
        }
        tr.getContent().add(tc);
    }
    //设置背景色
    public static String getBgColor(int i, int j){
    	if(j==1&&i==1){
    		return "D9D9D9";
    	}else if(j==1&&i==2){
    		return "C2D69B";
    	}else if(j==2&&i==1){
    		return "D9D9D9";
    	}else if(j==3&&i==1){
    		return "D9D9D9";
    	}else if(j==4&&i==1){
    		return "D9D9D9";
    	}else if(j==5){
    		return "D9D9D9";
    	}else{
    		return "FFFFFF";
    	}
    }
    /** 
     * 功能描述：合并单元格</BR> 
     *          表示合并第startRow（开始行）行中的第startCol（开始列）列到（startCol + colSpan - 1）列 </BR> 
     *          表示合并第startCol（开始列）行中的第startRow（开始行）列到（startRow + rowSpan - 1）行 
     * @param tc          单元格对象 
     * @param currentRow  当前行号，传入的是遍历表格时的行索引参数 
     * @param startRow    开始行 
     * @param rowSpan     合并的行数，大于1才表示合并 
     * @param currentCol  当前列号，传入的是遍历表格时的列索引参数 
     * @param startCol    开始列 
     * @param colSpan     合并的列数，大于1才表示合并 
     */  
    public static void setCellMerge(Tc tc , int currentRow , int startRow , int rowSpan , int currentCol , int startCol , int colSpan){  
        ObjectFactory factory = Context.getWmlObjectFactory();  
        TcPr tcpr = tc.getTcPr();  
        if(tcpr == null){  
            tcpr = factory.createTcPr();  
        }  
        //表示合并列  
        if(colSpan > 1){  
            //表示从第startRow行开始  
            if(currentRow == startRow){  
                //表示从第startRow行的第startCol列开始合并，合并到第startCol + colSpan - 1列  
                if(currentCol == startCol){  
                    HMerge hm = factory.createTcPrInnerHMerge();  
                    hm.setVal("restart");  
                    tcpr.setHMerge(hm);  
                    tc.setTcPr(tcpr);  
                }else if(currentCol > startCol && currentCol <= (startCol + colSpan - 1)){  
                    HMerge hm = factory.createTcPrInnerHMerge();  
                    tcpr.setHMerge(hm);  
                    tc.setTcPr(tcpr);  
                }  
            }  
        }  
        //表示合并行  
        if(rowSpan > 1){  
            //表示从第startCol列开始  
            if(currentCol == startCol){  
                //表示从第startCol列的第startRow行始合并，合并到第startRow + rowSpan - 1行  
                if(currentRow == startRow){  
                    VMerge vm = factory.createTcPrInnerVMerge();  
                    vm.setVal("restart");  
                    tcpr.setVMerge(vm);  
                    tc.setTcPr(tcpr);  
                }else if(currentRow > startRow && currentRow <= (startRow + rowSpan - 1)){  
                    VMerge vm = factory.createTcPrInnerVMerge();  
                    tcpr.setVMerge(vm);  
                    tc.setTcPr(tcpr);  
                }  
            }  
        }  
          
    }  
      
    /** 
     * 功能描述：合并单元格，相当于跨列的效果</BR> 
     *          表示合并第startRow（开始行）行中的第startCol（开始列）列到（startCol + colSpan - 1）列 </BR> 
     * @param tc          单元格对象 
     * @param currentRow  当前行号，传入的是遍历表格时的行索引参数 
     * @param startRow    开始行 
     * @param currentCol  当前列号，传入的是遍历表格时的列索引参数 
     * @param startCol    开始列 
     * @param colSpan     合并的列数，大于1才表示合并 
     * @author myclover 
     */  
    public static void setCellHMerge(Tc tc , int currentRow , int startRow , int currentCol , int startCol , int colSpan){  
        setCellMerge(tc, currentRow, startRow, 1, currentCol, startCol, colSpan);  
    }
}
