package com.mega.tank.pd;

import com.mega.tank.bridge.Boy;
import com.mega.tank.bridge.Flower;
import com.mega.tank.bridge.Girl;
import com.mega.tank.bridge.WarmGift;

public class BridgeClient {
    public static void main(String[] args) {
        Boy boy = new Boy();
        Girl girl = new Girl();
        boy.chase(girl, new WarmGift(new Flower("玫瑰花")));
    }
}
