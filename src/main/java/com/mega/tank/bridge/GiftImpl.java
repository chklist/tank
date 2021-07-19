package com.mega.tank.bridge;

public class GiftImpl {
    private String name;

    GiftImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GiftImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
