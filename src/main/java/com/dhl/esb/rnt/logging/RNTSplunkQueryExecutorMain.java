package com.dhl.esb.rnt.logging;


import com.dhl.esb.rnt.logging.helper.ExcelGenerator;
import com.dhl.esb.rnt.logging.helper.LogExtractionHelper;
import com.dhl.esb.rnt.logging.init.SplunkSDKConnector;
import com.dhl.esb.rnt.logging.model.ExcelDataModel;
import com.dhl.esb.rnt.logging.util.Constant;
import com.dhl.esb.rnt.logging.util.ExternalConfReader;
import com.dhl.esb.rnt.logging.util.InitConfig;
import com.dhl.esb.rnt.logging.util.UtiMethods;
import com.splunk.Args;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * @created 27/07/2023 - 12:26 PM
 * @project RNTSplunkQueryExecutor
 * @author Gayan Sanjeewa
 */
public class RNTSplunkQueryExecutorMain {
    final static Logger logger = Logger.getLogger(RNTSplunkQueryExecutorMain.class);

    static {
        logger.debug("loading property file");
        InitConfig.properties = ExternalConfReader.loadPropertyFile();
        logger.debug("finished loading property file");
    }

    public static void main(String[] args) {
        try {
            if (Objects.nonNull(InitConfig.properties)) {
                SplunkSDKConnector.init();
                List<ExcelDataModel> dataModelList = new ArrayList<>();
                Args sessionQueryArgs = UtiMethods.generateSessionQueryArgs();
                String queryString = "*RNT Final Transliterated Data*";

                String logDataQuery = "search sourcetype=" + Constant.SP_Q_SOURCE_TYPE + " Message=\"" + queryString + "\"";

                List<JSONObject> jobResultLogDataList = SplunkSDKConnector.createAndExecuteJob(logDataQuery, sessionQueryArgs);

                if (CollectionUtils.isNotEmpty(jobResultLogDataList)) {
                    for (JSONObject jsonObject : jobResultLogDataList) {
                        JSONArray resultsJsnObjArr = jsonObject.getJSONArray("results");
                        for (int i = 0; i < resultsJsnObjArr.length(); i++) {
                            JSONObject jsnObjResult = resultsJsnObjArr.getJSONObject(i);
                            if (jsnObjResult.has("Message")) {
                                String logLineMessage = jsnObjResult.getString("Message");
                                System.out.println(logLineMessage);

                                if (logLineMessage.contains(Constant.LOG_DATA_REF_SHIPPER_NAME) ||
                                        logLineMessage.contains(Constant.LOG_DATA_REF_SHIPPER_CONTACT_NAME) ||
                                        logLineMessage.contains(Constant.LOG_DATA_REF_CONSIGNEE_ORG_NAME) ||
                                        logLineMessage.contains(Constant.LOG_DATA_REF_CONSIGNEE_CONTACT_NAME)) {
                                    dataModelList.add(LogExtractionHelper.extractLogToExcelDataModel(logLineMessage));
                                }
                            }
                        }
                    }
                } else {
                    logger.debug("Empty result from session query");
                }

                //generating the excel file
                String fileName = ExcelGenerator.writeExcelAndSave(dataModelList);
                logger.debug("excel filed successfully generated and saved - " + fileName);
            } else {
                logger.debug("unable to run the jar file- null property source");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }
}