package com.mega.tank.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 1.单例模式
 */
public class PropertyMgr {
    private static volatile PropertyMgr INSTANCE;
    private static Properties properties = new Properties();

    private PropertyMgr() {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertyMgr getInstance() {
        if (INSTANCE == null) {
            synchronized (PropertyMgr.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PropertyMgr();
                }
            }
        }
        return INSTANCE;
    }

    public Object get(String key) {
        return properties.get(key);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }
}
