package com.cherryframe.cherryframe.dao.config.impl;

import com.cherryframe.cherryframe.CherryFrameApplication;
import com.cherryframe.cherryframe.dao.config.CPMSConfigurationService;

import java.io.InputStream;
import java.util.Properties;

public class CPMSConfigurationServiceImpl implements CPMSConfigurationService {

    private static final String CONFIGURATION_PROPERTIES_PATH = "configuration.properties";
    private static final InputStream CONFIG_STREAM = CherryFrameApplication.class.getResourceAsStream(CONFIGURATION_PROPERTIES_PATH);
    private static final Properties properties = new Properties();

    @Override
    public String getValue(String key) {
        loadProperties();
        return properties.getProperty(key);
    }

    private void loadProperties() {
        if (properties.isEmpty()) {
            try {
                properties.load(CONFIG_STREAM);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}
