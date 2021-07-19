package com.mega.tank.bridge;

abstract class Gift {
    GiftImpl giftImpl;

    GiftImpl getGiftImpl() {
        return giftImpl;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "giftImpl=" + giftImpl +
                '}';
    }
}
