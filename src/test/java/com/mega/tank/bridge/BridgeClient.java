package com.mega.tank.bridge;

public class BridgeClient {
    public static void main(String[] args) {
        Boy boy = new Boy();
        Girl girl = new Girl();
        boy.chase(girl, new WarmGift(new Flower("玫瑰花")));
    }
}
