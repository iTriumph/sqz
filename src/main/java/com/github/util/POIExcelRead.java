package com.github.util;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POIExcelRead {

	private static final String XLS = "xls";
	private static final String XLSX = "xlsx";

	public static void main(String[] args) throws IOException {

		String filePath = "/Users/imiracle/Desktop/import.xlsx";
		File file = new File(filePath);

		if (filePath.endsWith(XLS)) {
			ReadXLS(file);
		} else if (filePath.endsWith(XLSX)) {
			ReadXLSX(file);
		} else {
			String msg = "文件格式错误，请上传正确的文件！";
			//return msg;
		}
	}

	public static void ReadXLS(File file) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
		HSSFSheet sheet = workbook.getSheetAt(0);//默认获取第一个sheet
		int lastRowNum = sheet.getLastRowNum();//最后一行行号
		System.out.println(lastRowNum);

		for (int i = 0; i <= lastRowNum; i++) {//遍历行
			HSSFRow row = sheet.getRow(i);
			int lastCellNum = row.getLastCellNum();//获取当前行最后的单元格
			for (int j = 0; j < lastCellNum; j++) {
				HSSFCell cell = row.getCell(j);
				String value = cell.getStringCellValue();
				System.out.print(value + "  ");
			}
			System.out.println();
		}

		workbook.close();
	}

	public static List<List<String>> ReadXLSX(File file) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(FileUtils.openInputStream(file));
		XSSFSheet sheet = workbook.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum();
		System.out.println(lastRowNum);

		List<List<String>> userList = new ArrayList<>();
		for (int i = 1; i <= lastRowNum; i++) {

			XSSFRow row = sheet.getRow(i);
			Map<String, String> userMap = new HashMap<>();
			List<String> user = new ArrayList<>();

			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {

				Cell cell = row.getCell(j);
				if (cell == null) {
					continue;
				}

				String value = null;
				CellType cellType = cell.getCellTypeEnum();

				switch (cellType) {
					case STRING:
						value = cell.getStringCellValue();
						break;
					case NUMERIC:
						DecimalFormat decimalFormat = new DecimalFormat("#");
						value =decimalFormat.format(cell.getNumericCellValue());
						break;
					default:
						System.err.println("error...: " + i + "  " + cell.getRowIndex() + "  " + user);
				}
				user.add(value);
			}

			userList.add(user);
		}

		workbook.close();

		return userList;
	}

}