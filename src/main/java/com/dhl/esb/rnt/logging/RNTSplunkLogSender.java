package com.dhl.esb.rnt.logging;


import com.splunk.*;

/*
 * @created 27/07/2023 - 12:26 PM
 * @project RNTSplunkQueryExecutor
 * @author Gayan Sanjeewa
 */
public class RNTSplunkLogSender {
    public static void main(String[] args) {
        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
        ServiceArgs serviceArgs = new ServiceArgs();
        serviceArgs.setHost("esb-test-splunk.dhl.com");
        serviceArgs.setUsername("wigamage");
        serviceArgs.setPassword("123456789");
        serviceArgs.setPort(8089);

        Service service = Service.connect(serviceArgs);
        Receiver receiver = service.getReceiver();
        Args args1 = new Args();
        args1.put("sourcetype", "dev");
        receiver.log("esb_uat", args1, "Test RNT - Login");

    }
}