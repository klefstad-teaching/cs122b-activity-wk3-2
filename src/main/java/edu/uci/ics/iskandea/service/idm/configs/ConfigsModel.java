package edu.uci.ics.iskandea.service.idm.configs;

import java.util.Map;

public class ConfigsModel
{
    private Map<String, String> serviceConfig;
    private Map<String, String> loggerConfig;
    private Map<String, String> databaseConfig;

    public Map<String, String> getServiceConfig()
    {
        return serviceConfig;
    }

    public void setServiceConfig(Map<String, String> serviceConfig)
    {
        this.serviceConfig = serviceConfig;
    }

    public Map<String, String> getLoggerConfig()
    {
        return loggerConfig;
    }

    public void setLoggerConfig(Map<String, String> loggerConfig)
    {
        this.loggerConfig = loggerConfig;
    }

    public Map<String, String> getDatabaseConfig()
    {
        return databaseConfig;
    }

    public void setDatabaseConfig(Map<String, String> databaseConfig)
    {
        this.databaseConfig = databaseConfig;
    }
}