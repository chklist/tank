package com.mega.tank.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {

    private static Map<String, Flyweight> flyweights = new HashMap<>();

    private FlyweightFactory() {
    }

    public static Flyweight getFlyweight(String uuid) {
        if (flyweights.containsKey(uuid)) {
            return flyweights.get(uuid);
        } else {
            FlyweightImpl flyweight = new FlyweightImpl(uuid);
            flyweights.put(uuid, flyweight);
            return flyweight;
        }
    }


    private static class FlyweightImpl implements Flyweight {

        private String uuid;

        private String content;

        FlyweightImpl(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }

        @Override
        public String getContent() {
            return content;
        }

        @Override
        public void setContent(String content) {
            this.content = content;
        }
    }
}
