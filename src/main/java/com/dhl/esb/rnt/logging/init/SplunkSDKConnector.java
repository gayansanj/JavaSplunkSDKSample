package com.dhl.esb.rnt.logging.init;

import com.dhl.esb.rnt.logging.util.Constant;
import com.dhl.esb.rnt.logging.util.InitConfig;
import com.splunk.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * @created 01/08/2023 - 10:05 AM
 * @project RNTSplunkQueryExecutor
 * @author Gayan Sanjeewa
 */
public class SplunkSDKConnector {

    private SplunkSDKConnector() {
    }

    final static Logger logger = Logger.getLogger(SplunkSDKConnector.class);
    private static Service service = null;

    public static void init() {
        try {
            if (Objects.nonNull(InitConfig.properties)) {
                String host = InitConfig.properties.getProperty("splunk.sdk.host");
                String userName = InitConfig.properties.getProperty("splunk.sdk.userName");
                String password = InitConfig.properties.getProperty("splunk.sdk.password");
                String port = InitConfig.properties.getProperty("splunk.sdk.port");

                if (
                        StringUtils.isNotBlank(host) &&
                                StringUtils.isNotBlank(userName) &&
                                StringUtils.isNotBlank(password) &&
                                StringUtils.isNotBlank(port)) {
                    logger.debug("Start Splunk SDK Initialization - " + host);
                    HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
                    ServiceArgs serviceArgs = new ServiceArgs();
                    serviceArgs.setHost(host);
                    serviceArgs.setUsername(userName);
                    serviceArgs.setPassword(new String(Base64.decodeBase64(password)));
                    serviceArgs.setPort(Integer.parseInt(port));
                    logger.debug("Splunk SDK Initialization success");
                    service = Service.connect(serviceArgs);
                }

            } else {
                logger.debug("unable to initialized the splunk sdk - null property source");
            }
        } catch (Exception ex) {
            logger.error("exception occurred while initializing the splunk sdk : ", ex);
        }
    }

    public static int getMaxResultRows() {
        if (Objects.nonNull(service)) {
            Entity restApi = service.getConfs().get(Constant._SP_LIMITS).get(Constant._SP_REST_API);
            return Integer.parseInt((String) restApi.get(Constant._SP_MAX_ROWS));
        } else {
            logger.debug("Failed to get the rows - Splunk SDK is not initialized");
        }
        return 0;
    }

    public static List<JSONObject> createAndExecuteJob(String query, Args queryArgs) throws InterruptedException, IOException {
        if (Objects.nonNull(service)) {
            logger.debug("creating a splunk job to execute the query");
            List<JSONObject> jobResultList = new ArrayList<>();
            Job job = service.getJobs().create(query, queryArgs);
            int maxResultRows = getMaxResultRows();
            logger.debug("job created with the query - " + query);
            while (!job.isDone()) {
                logger.debug("searching.. waiting for the result.");
                Thread.sleep(500);
            }
            int jobEventCount = job.getEventCount();
            logger.debug("search result received with event count - " + jobEventCount);
            int offset = 0;
            Args outArgs = new Args();
            outArgs.put(Constant._SP_OUT_MODE, Constant._SP_JSON);
            while (offset < jobEventCount) {
                outArgs.put(Constant._SP_COUNT, maxResultRows);
                outArgs.put(Constant._SP_OFFSET, offset);
                InputStream results = job.getResults(outArgs);
                String resultString = IOUtils.toString(results, StandardCharsets.UTF_8);
                offset = offset + maxResultRows;
                jobResultList.add(new JSONObject(resultString));
            }
            return jobResultList;
        } else {
            logger.debug("Failed to create the JOB - Splunk SDK is not initialized");
        }
        return null;
    }
}
