package com.mega.tank.net.message;

import java.io.IOException;

public abstract class Msg {
    public abstract void handle();

    public abstract byte[] toBytes();

    public abstract void parse(byte[] bytes) throws IOException;

    public abstract MsgType getMsgType();
}
