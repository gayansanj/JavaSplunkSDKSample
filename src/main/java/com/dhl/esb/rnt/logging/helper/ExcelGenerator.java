package com.dhl.esb.rnt.logging.helper;

import com.dhl.esb.rnt.logging.model.ExcelDataModel;
import com.dhl.esb.rnt.logging.util.Constant;
import com.dhl.esb.rnt.logging.util.InitConfig;
import com.dhl.esb.rnt.logging.util.UtiMethods;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

/*
 * @created 31/07/2023 - 2:35 PM
 * @project RNTSplunkQueryExecutor
 * @author Gayan Sanjeewa
 */
public class ExcelGenerator {
    private static String[] columns =
            {
                    "Type",
                    "Customer Account No",
                    "AWB",
                    "Shipper Country",
                    "Consignee Country",
                    "Language Code",
                    "Language Script Code",
                    "Original Text",
                    "Transliterated Text",
                    "Confidence"
            };

    public static String writeExcelAndSave(List<ExcelDataModel> dataArrayList) throws Exception {
        String filePath = InitConfig.properties.getProperty("excel.file.path");
        if (StringUtils.isNotBlank(filePath)) {
            // Create a Workbook
            Workbook workbook = new XSSFWorkbook();

            // Create a Sheet
            Sheet sheet = workbook.createSheet("RNT Transliterated Data");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            Row headerRow = sheet.createRow(0);
            // Create cells
            int rowNum = 1;
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            for (ExcelDataModel excelDataModel : dataArrayList) {
                Row row = sheet.createRow(rowNum++);
                int i = 0;
                row.createCell(i)
                        .setCellValue(excelDataModel.getType().getTypeName());
                row.createCell(++i)
                        .setCellValue(excelDataModel.getCustomerAcNumber());
                row.createCell(++i)
                        .setCellValue(excelDataModel.getAwd());
                row.createCell(++i)
                        .setCellValue(excelDataModel.getShipperCountry());
                row.createCell(++i)
                        .setCellValue(excelDataModel.getConsigneeCountry());
                row.createCell(++i)
                        .setCellValue(excelDataModel.getLanguageCode());
                row.createCell(++i)
                        .setCellValue(excelDataModel.getLanguageScriptCode());
                row.createCell(++i)
                        .setCellValue(excelDataModel.getOriginalText());
                row.createCell(++i)
                        .setCellValue(excelDataModel.getTransliteratedText());
                row.createCell(++i)
                        .setCellValue(excelDataModel.getConfidence());

            }
            // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            String fileName = "RNT-log-extraction_" + UtiMethods.getCurrentDateDDMMYYYY() + ".xlsx";
            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(filePath + fileName);
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();
            return fileName;
        } else {
            return "N/A";
        }

    }
}
