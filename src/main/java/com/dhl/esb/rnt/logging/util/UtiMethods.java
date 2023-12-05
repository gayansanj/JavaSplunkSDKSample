package com.dhl.esb.rnt.logging.util;

import com.splunk.Args;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @created 01/08/2023 - 8:48 AM
 * @project RNTSplunkQueryExecutor
 * @author Gayan Sanjeewa
 */
public class UtiMethods {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");

    public static String getCurrentDateDDMMYYYY() {
        return simpleDateFormat.format(new Date());
    }

    public static Args generateSessionQueryArgs() {
        Args sessionQueryArgs = new Args();
        sessionQueryArgs.put(Constant._SP_EARLIEST_TIME, "-20d@d");
        sessionQueryArgs.put(Constant._SP_LATEST_TIME, "now");
        return sessionQueryArgs;
    }
}
