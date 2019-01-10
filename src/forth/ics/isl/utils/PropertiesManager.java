/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forth.ics.isl.utils;

/**
 *
 * @author mhalkiad
 */

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PropertiesManager {
    
    private static PropertiesManager propManager = null;
    private static final String initFilePath = "config.properties";
    private static Properties prop;
    
     public static PropertiesManager getPropertiesManager() {
        if (propManager == null) {
            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                InputStream input = classLoader.getResourceAsStream(initFilePath);
                prop = new Properties();
                if (input != null) {
                    prop.load(input);
                }
                propManager = new PropertiesManager();
            } catch (IOException ex) {
                Logger.getLogger(PropertiesManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return propManager;
    }

    public String getPropertyValue(String key) {
        return prop.getProperty(key);
    }

    public Properties getProperties() {
        return prop;
    }
    
    public String getTomcatLocation() {
        return prop.getProperty("tomcat.url");
    }

    public String getFcrepoLocation() {
        return prop.getProperty("fcrepo.url");
    }
}
