package com.dod.sport.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by Sugior on 2016/11/18.
 */
public class PropertiesUtil {
    private static String propertiesfile = "";
    private static Properties properties = new Properties();

    public PropertiesUtil() {
        // Cannot be instantiated
    }

    public PropertiesUtil(String propertiesFile) {
        loadFile(propertiesFile);
    }

    /**
     * 加载配置文件
     */
    public  void loadFile(String filePath) {
        try {
            propertiesfile = filePath;
            properties.load(new FileInputStream(propertiesfile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    /**
     * @param key
     * @return String
     */
    public  String getValue(String key) {
        return properties.getProperty(key);
    }
    /**
     * 写入value值
     * @param key
     * @param value
     */
    public  void setValue(String key, String value) {
        try {
            OutputStream foutStream = new FileOutputStream(propertiesfile);
            properties.setProperty(key, value);
            properties.store(foutStream,  "Update '" + key + "' value");
            foutStream.close();
        } catch (IOException e) {
            System.err.println("写入value值失败");
        }
    }

    /**
     * 根据key读取value
     * @param filePath
     * @param key
     */
    public static  String readProperties(String filePath, String key) {
        Properties props = new Properties();
        try {
            InputStream fileInStream = new FileInputStream(filePath);
            props.load(new BufferedInputStream(fileInStream));
            String value = props.getProperty(key);
            fileInStream.close();
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param filePath
     * @param keyName
     * @param keyValue
     */
    public static   void writeProperties(String filePath, String keyName, String keyValue) {
        try {
            Properties props = new Properties();
            FileInputStream fileInStream = new FileInputStream(filePath);
            props.load(new BufferedInputStream(fileInStream));
            OutputStream fileOutStream = new FileOutputStream(filePath);
            props.setProperty(keyName, keyValue);
            props.store(fileOutStream, "Write '" + keyName + "' value");
            fileOutStream.close();
            fileInStream.close();
        } catch (IOException e) {
            System.err.println("写入配置信息失败");
        }
    }


    public static void main(String[] args) {
        readProperties("mail.properties", "MAIL_SERVER_PASSWORD");
        writeProperties("mail.properties", "MAIL_SERVER_INCOMING", "test@qq.com");
        System.out.println("读写配置信息完成");
    }
}
