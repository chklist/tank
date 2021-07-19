package com.mega.tank.bridge;

public class Girl {
    void receive(Gift gift) {
        System.out.println("我喜欢这个礼物：" + gift.getGiftImpl().getName());
    }
}
