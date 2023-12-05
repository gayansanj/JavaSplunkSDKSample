package com.dhl.esb.rnt.logging.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

/*
 * @created 31/07/2023 - 3:03 PM
 * @project RNTSplunkQueryExecutor
 * @author Gayan Sanjeewa
 */
public class ExternalConfReader {

    public static Properties loadPropertyFile() {

        try (InputStream input = Files.newInputStream(Paths.get(Constant.CONF_FILE_PATH))) {

            Properties prop = new Properties();

            if (Objects.isNull(input)) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
