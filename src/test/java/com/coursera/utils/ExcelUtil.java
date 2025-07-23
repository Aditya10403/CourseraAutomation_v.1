package com.coursera.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    // for reading input data by row and col from xl
    public String readInputDataByRowAndCol(int row, int col) throws IOException {
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/test-data/input-data/CourseraData.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        XSSFRow xlRow = sheet.getRow(row);
        XSSFCell xlCell = xlRow.getCell(col);

        String cellValue;

        // for number
        if (xlCell.getCellType() == CellType.NUMERIC) {
            cellValue = String.valueOf((int) xlCell.getNumericCellValue());
        } else {
            cellValue = xlCell.toString();
        }

        workbook.close();
        file.close();

        return cellValue;
    }

    // for reading details of multiple people
    public List<String[]> readFormDetails() throws IOException {
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/test-data/input-data/FormDetails.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        List<String[]> details = new ArrayList<>();
        int rowCount = sheet.getPhysicalNumberOfRows();

        for (int i = 1; i < rowCount; i++) {
            XSSFRow row = sheet.getRow(i);
            String[] personDetails = new String[row.getPhysicalNumberOfCells()];
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                XSSFCell cell = row.getCell(j);
                if (cell.getCellType() == CellType.NUMERIC) {
                    cell.setCellType(CellType.STRING);
                    personDetails[j] = cell.getStringCellValue();
                } else {
                    personDetails[j] = cell.toString();
                }
            }
            details.add(personDetails);
        }

        workbook.close();
        file.close();

        return details;
    }

    // to write book results in xl
    public void writeOutputData(List<List<String>> data) throws IOException {
        String excelFilePath = getExcelFilePath();
        FileOutputStream file = new FileOutputStream(excelFilePath);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("CoursesResults");

        int totalRows = data.size();
        int totalColumns = data.getFirst().size();

        XSSFRow row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("SNO");
        row1.createCell(1).setCellValue("TITLE");
        row1.createCell(2).setCellValue("RATING");
        row1.createCell(3).setCellValue("DURATION");

        for (int i = 0; i < totalRows; i++) {
            XSSFRow row = sheet.createRow(i + 1);
            for (int j = 0; j < totalColumns; j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(data.get(i).get(j));
            }
        }

        workbook.write(file);
        workbook.close();
        file.close();
    }

    // create xl file path
    private String getExcelFilePath() {
        return System.getProperty("user.dir") + "/test-data/output-data/CoursesResult.xlsx";
    }
}
