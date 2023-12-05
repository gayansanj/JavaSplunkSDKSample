package com.dhl.esb.rnt.logging.helper;

import com.dhl.esb.rnt.logging.model.ExcelDataModel;
import com.dhl.esb.rnt.logging.util.TypeEnum;

/*
 * @created 01/08/2023 - 12:58 PM
 * @project RNTSplunkQueryExecutor
 * @author Gayan Sanjeewa
 */
public class LogExtractionHelper {
    private LogExtractionHelper() {

    }

    public static ExcelDataModel extractLogToExcelDataModel(String logLine) {
        String[] splitLogLine = logLine.split("\\|");
        ExcelDataModel excelDataModel = new ExcelDataModel();
        int i = 0;
        excelDataModel.setType(TypeEnum.fromValue(splitLogLine[++i]));
        excelDataModel.setCustomerAcNumber(splitLogLine[++i]);
        excelDataModel.setAwd(splitLogLine[++i]);
        excelDataModel.setShipperCountry(splitLogLine[++i]);
        excelDataModel.setConsigneeCountry(splitLogLine[++i]);
        excelDataModel.setLanguageCode(splitLogLine[++i]);
        excelDataModel.setLanguageScriptCode(splitLogLine[++i]);
        excelDataModel.setOriginalText(splitLogLine[++i]);
        excelDataModel.setTransliteratedText(splitLogLine[++i]);
        excelDataModel.setConfidence(splitLogLine[++i]);
        return excelDataModel;
    }
}
