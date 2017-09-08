package com.gxchan.dbdic.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class Config {
    private String templateDir;
    private String targetDir;
    private String outputFile;
    private static Config instance = new Config();

    private Config() {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource("config.properties");
            properties.load(new FileInputStream(url.getFile()));
            this.templateDir = properties.getProperty("template_dir");
            this.targetDir = properties.getProperty("target_dir");
            this.outputFile = properties.getProperty("output_file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        return instance;
    }

    
    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

}
