package com.changxx.practice.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesTest {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
        properties.load(in);
        String driver = properties.getProperty("serverside.jdbc.driver");
        System.out.println("driver: " + driver);

        for (Entry<Object, Object> entry : properties.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
