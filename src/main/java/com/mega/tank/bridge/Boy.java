package com.mega.tank.bridge;

public class Boy {
    public void chase(Girl girl, Gift gift) {
        give(girl, gift);
    }

    private void give(Girl girl, Gift gift) {
        girl.receive(gift);
    }
}
