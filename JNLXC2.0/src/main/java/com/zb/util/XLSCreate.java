package com.zb.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;


import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class XLSCreate {

	public static File writeRow(List list, String title) {
		try {
            File xlsFile=new File(title + ".xls");
			WritableWorkbook wwb = Workbook.createWorkbook(xlsFile);// 建立excel文本
			WritableSheet ws = wwb.createSheet("第一页", 0);// 建立sheet页 0表示第一页
			Class clazz=list.get(0).getClass();
			Field[] field = clazz.getDeclaredFields();
			int row = list.size();
			int fieldLength=field.length;
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 15,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.CORAL); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableFont wf3 = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf = new WritableCellFormat(wf);
			WritableCellFormat wcf2 = new WritableCellFormat(wf2);// 单元格定义
			WritableCellFormat wcf3 = new WritableCellFormat(wf3);// 单元格定义

			// wcf.setBackground(jxl.format.Colour.BLACK); // 设置单元格的背景颜色
			wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
			wcf2.setAlignment(jxl.format.Alignment.CENTRE);
			wcf3.setAlignment(jxl.format.Alignment.CENTRE);

			ws.setColumnView(1, 15); // 设置列的宽度
			ws.setColumnView(3, 14); // 设置列的宽度
			ws.setColumnView(8, 22); // 设置列的宽度
			ws.setColumnView(2, 15); // 设置列的宽度
			ws.setColumnView(9, 15); // 设置列的宽度
			

			ws.mergeCells(0, 0, fieldLength-1, 0); // 合并单元格，从第i行，第i列到top.length行
			ws.addCell(new Label(0, 0, title, wcf));// 给第i行第i列的单元格写数据”title“，并添加到第一页中;
			for (int k = 0; k < fieldLength; k++) {
				ws.addCell(new Label(k, 1, field[k].getName(), wcf3));
			}
			for (int i = 0; i <row; i++) {
				Object o=list.get(i);
				int j=0;
				for(Field f:field){
					String methodName="get"+Tools.firstUpcase(field[j].getName());
					String result =  clazz.getMethod(methodName).invoke(o).toString();
					ws.addCell(new Label(j, i+2, result, wcf2));// 给第j列第i行的单元格写数据（list..），并添加到第一页中
				    j++;
				}
					
			}

			wwb.write();
			wwb.close();
			String flag = title+"报表生成";
			System.out.println(flag);
			return xlsFile;
		} catch (Exception e) {
			String flag = e.toString();
			System.out.println(flag);
			return null;
		}

	}
}
