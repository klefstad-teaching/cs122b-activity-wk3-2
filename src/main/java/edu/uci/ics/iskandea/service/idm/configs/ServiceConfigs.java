package edu.uci.ics.iskandea.service.idm.configs;

import edu.uci.ics.iskandea.service.idm.logger.ServiceLogger;

public class ServiceConfigs
{
    // Port Ranges
    private static final int MIN_SERVICE_PORT       = 1024;
    private static final int MAX_SERVICE_PORT       = 65535;

    // Default gateway configs
    private static final String DEFAULT_SCHEME      = "http://";
    private static final String DEFAULT_HOSTNAME    = "0.0.0.0";
    private static final int    DEFAULT_PORT        = 2942;
    private static final String DEFAULT_PATH        = "/activity";

    // Default logger configs
    private static final String DEFAULT_OUTPUT_DIR  = "./logs/";
    private static final String DEFAULT_OUTPUT_FILE = "test.log";

     // Service configs
    private String scheme;
    private String hostName;
    private int    port;
    private String path;

    // Logger configs
    private String outputDir;
    private String outputFile;

    // Database configs
    private String dbUsername;
    private String dbPassword;
    private String dbHostname;
    private int dbPort;
    private String dbName;
    private String dbDriver;
    private String dbSettings;

    // If any DB configs are invalid, set this to false
    private boolean dbConfigValid = true;

    // Session configs
    private long timeout;
    private long expiration;

    public ServiceConfigs() { }

    public ServiceConfigs(ConfigsModel cm)
            throws NullPointerException
    {
        if (cm == null) {
            ServiceLogger.LOGGER.severe("ConfigsModel not found.");
            throw new NullPointerException("ConfigsModel not found.");
        }

        setServiceConfigs(cm);
        setLoggerConfigs(cm);
        setDataBaseConfigs(cm);
    }

    private void setServiceConfigs(ConfigsModel cm)
    {
        scheme = cm.getServiceConfig().get("scheme");
        if (scheme == null){
            scheme = DEFAULT_SCHEME;
            System.err.println("Scheme not found in configuration file. Using default.");

        } else {
            System.err.println("Scheme: " + scheme);

        }

        hostName = cm.getServiceConfig().get("hostName");
        if (hostName == null){
            hostName = DEFAULT_HOSTNAME;
            System.err.println("Hostname not found in configuration file. Using default.");

        } else {
            System.err.println("Hostname: " + hostName);

        }

        port = Integer.parseInt(cm.getServiceConfig().get("port"));
        if (port == 0) {
            port = DEFAULT_PORT;
            System.err.println("Port not found in configuration file. Using default.");

        } else if (port < MIN_SERVICE_PORT || port > MAX_SERVICE_PORT) {
            port = DEFAULT_PORT;
            System.err.println("Port is not within valid range. Using default.");

        } else {
            System.err.println("Port: " + port);

        }

        path = cm.getServiceConfig().get("path");
        if (path == null) {
            path = DEFAULT_PATH;
            System.err.println("Path not found in configuration file. Using default.");

        } else {
            System.err.println("Path: " + path);

        }
    }

    private void setLoggerConfigs(ConfigsModel cm)
    {
        outputDir = cm.getLoggerConfig().get("outputDir");
        if (outputDir == null) {
            outputDir = DEFAULT_OUTPUT_DIR;
            System.err.println("Logging output directory not found in configuration file. Using default.");

        } else {
            System.err.println("Logging output directory: " + outputDir);

        }

        outputFile = cm.getLoggerConfig().get("outputFile");
        if (outputFile == null) {
            outputFile = DEFAULT_OUTPUT_FILE;
            System.err.println("Logging output file not found in configuration file. Using default.");

        } else {
            System.err.println("Logging output file: " + outputFile);

        }
    }

    private void setDataBaseConfigs(ConfigsModel cm)
    {
        dbUsername = cm.getDatabaseConfig().get("dbUsername");
        if (dbUsername == null) {
            System.err.println("No database username found in configuration file.");
            dbConfigValid = false;

        } else {
            System.err.println("Database username: " + dbUsername);

        }

        dbPassword = cm.getDatabaseConfig().get("dbPassword");
        if (dbPassword == null) {
            System.err.println("No database password found in configuration file.");
            dbConfigValid = false;

        } else {
            System.err.println("Database password found in configuration file.");

        }

        dbHostname = cm.getDatabaseConfig().get("dbHostname");
        if (dbHostname == null) {
            System.err.println("No database hostname found in configuration file.");
            dbConfigValid = false;

        } else {
            System.err.println("Database hostname: " + dbHostname);

        }

        dbPort = Integer.parseInt(cm.getDatabaseConfig().get("dbPort"));
        if (dbPort == 0) {
            System.err.println("No database port found in configuration file.");
            dbConfigValid = false;

        } else if (dbPort < MIN_SERVICE_PORT || dbPort > MAX_SERVICE_PORT) {
            System.err.println("Database port is not within a valid range.");
            dbConfigValid = false;

        } else {
            System.err.println("Database port: " + dbPort);

        }

        dbName = cm.getDatabaseConfig().get("dbName");
        if (dbName == null) {
            System.err.println("No database name found in configuration file.");
            dbConfigValid = false;

        } else {
            System.err.println("Database name: " + dbName);

        }

        dbDriver = cm.getDatabaseConfig().get("dbDriver");
        if (dbDriver == null) {
            System.err.println("No driver found in configuration file.");
            dbConfigValid = false;

        } else {
            System.err.println("Database driver: " + dbDriver);

        }

        dbSettings = cm.getDatabaseConfig().get("dbSettings");
        if (dbSettings == null) {
            System.err.println("No connection settings found in configuration file.");
            dbConfigValid = false;

        } else {
            System.err.println("Database connection settings: " + dbSettings);

        }
    }

    public void currentConfigs()
    {
        ServiceLogger.LOGGER.config("Scheme: " + scheme);
        ServiceLogger.LOGGER.config("Hostname: " + hostName);
        ServiceLogger.LOGGER.config("Port: " + port);
        ServiceLogger.LOGGER.config("Path: " + path);
        ServiceLogger.LOGGER.config("Logger output directory: " + outputDir);
        ServiceLogger.LOGGER.config("Database hostname: " + dbHostname);
        ServiceLogger.LOGGER.config("Database port: " + dbPort);
        ServiceLogger.LOGGER.config("Database username: " + dbUsername);
        ServiceLogger.LOGGER.config("Database password provided? " + (dbPassword != null));
        ServiceLogger.LOGGER.config("Database name: " + dbName);
        ServiceLogger.LOGGER.config("Database driver: " + dbDriver);
        ServiceLogger.LOGGER.config("Database connection settings: " + dbSettings);
    }

    public String getScheme()
    {
        return scheme;
    }

    public String getHostName()
    {
        return hostName;
    }

    public Integer getPort()
    {
        return port;
    }

    public String getPath()
    {
        return path;
    }

    public String getOutputDir()
    {
        return outputDir;
    }

    public String getOutputFile()
    {
        return outputFile;
    }

    public String getDbUrl() {
        return "jdbc:mysql://" + dbHostname + ":" + dbPort + "/" + dbName + dbSettings;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbHostname() {
        return dbHostname;
    }

    public int getDbPort() {
        return dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbSettings() {
        return dbSettings;
    }

    public boolean isDbConfigValid() {
        return dbConfigValid;
    }
}