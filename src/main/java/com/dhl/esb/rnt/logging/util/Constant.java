package com.dhl.esb.rnt.logging.util;

/*
 * @created 31/07/2023 - 11:54 AM
 * @project RNTSplunkQueryExecutor
 * @author Gayan Sanjeewa
 */
public class Constant {
    //    public static String LOG_DATA_REF_SHIPPER_NAME = "Tranliteration origin country code : ";
    public static String LOG_DATA_REF_SHIPPER_NAME = "RNT Final Transliterated Data [ShipperName]";
    public static String LOG_DATA_REF_SHIPPER_CONTACT_NAME = "RNT Final Transliterated Data [ShipperContactName]";
    public static String LOG_DATA_REF_CONSIGNEE_ORG_NAME = "RNT Final Transliterated Data [ConsigneeOrgName]";
    public static String LOG_DATA_REF_CONSIGNEE_CONTACT_NAME = "RNT Final Transliterated Data [ConsigneeContactName]";

    public static String CONF_FILE_PATH = "/appl/rosette_script/conf/pilot_logging/conf/config.properties";
    public static String SP_Q_SERVICE_NAME = "ShipmentManagement.ShipmentManifestProvider_v4";
    public static String SP_Q_SOURCE_TYPE = "prod";

    public final static String _SP_LIMITS = "limits";
    public final static String _SP_REST_API = "restapi";
    public final static String _SP_MAX_ROWS = "maxresultrows";
    public final static String _SP_OUT_MODE = "output_mode";
    public final static String _SP_JSON = "json";
    public final static String _SP_COUNT = "count";
    public final static String _SP_OFFSET = "offset";
    public final static String _SP_EARLIEST_TIME = "earliest_time";
    public final static String _SP_LATEST_TIME = "latest_time";
}
