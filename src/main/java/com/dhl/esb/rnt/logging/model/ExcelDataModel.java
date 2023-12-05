package com.dhl.esb.rnt.logging.model;

/*
 * @created 31/07/2023 - 12:43 PM
 * @project RNTSplunkQueryExecutor
 * @author Gayan Sanjeewa
 */

import com.dhl.esb.rnt.logging.util.TypeEnum;
import lombok.Data;

@Data
public class ExcelDataModel {
    private TypeEnum type;
    private String customerAcNumber;
    private String awd;
    private String shipperCountry;
    private String consigneeCountry;
    private String languageCode;
    private String languageScriptCode;
    private String originalText;
    private String transliteratedText;
    private String confidence;
    private String sessionId;
}
