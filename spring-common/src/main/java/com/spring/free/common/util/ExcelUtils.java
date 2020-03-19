package com.spring.free.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ExcelUtils {

	/**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}

        //创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values[i].length; j++) {
				// 将内容按顺序赋给对应的列对象
				row.createCell(j).setCellValue(values[i][j]);
			}
		}
        return wb;
    }
	
	public static Workbook readExcel(String filePath) {
		Workbook wb = null;
		if (filePath == null) {
			return null;
		}
		String extString = filePath.substring(filePath.lastIndexOf("."));
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			if (".xls".equalsIgnoreCase(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equalsIgnoreCase(extString)) {
				return wb = new XSSFWorkbook(is);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	public static Workbook readExcel(byte[] file, String fileName) {
		Workbook wb = null;
		if (file == null || fileName == null) {
			return null;
		}
		String extString = fileName.substring(fileName.lastIndexOf("."));
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(file);
			if (".xls".equalsIgnoreCase(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equalsIgnoreCase(extString)) {
				return wb = new XSSFWorkbook(is);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	public static Workbook readExcel(MultipartFile file) {
		Workbook wb = null;
		if (file == null) {
			return null;
		}
		String fileName = file.getOriginalFilename();
		String extString = fileName.substring(fileName.lastIndexOf("."));
		InputStream is = null;
		try {
			is = file.getInputStream();
			if (".xls".equalsIgnoreCase(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equalsIgnoreCase(extString)) {
				return wb = new XSSFWorkbook(is);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	public static String getCellValue(Cell cell) {
		if(cell == null) return "";
		switch(cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_FORMULA:
				return cell.getCellFormula();
			case Cell.CELL_TYPE_BLANK:
				return "";
			case Cell.CELL_TYPE_BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case Cell.CELL_TYPE_ERROR:
				return "";
		}
		return "";
	}
	
	public static void setCellValue(Cell newCell, Cell orgCell) {
		if(orgCell == null || newCell == null) return;
		switch(orgCell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			newCell.setCellValue(orgCell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			newCell.setCellValue(orgCell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			newCell.setCellValue("");
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			newCell.setCellValue(orgCell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			newCell.setCellFormula(orgCell.getCellFormula());
			break;
		case Cell.CELL_TYPE_ERROR:
			newCell.setCellErrorValue(orgCell.getErrorCellValue());
		}
	}
}
