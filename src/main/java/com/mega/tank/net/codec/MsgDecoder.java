package com.mega.tank.net.codec;

import com.mega.tank.net.message.Msg;
import com.mega.tank.net.message.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 消息解码器
 */
public class MsgDecoder extends ByteToMessageDecoder {

    private static final String MSG_CLASS_PACKAGE = "com.mega.tank.net.message";
    private static final String MSG_CLASS_SUFFIX = "Msg";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 处理TCP拆包粘包问题
        if (in.readableBytes() < 8) return;
        in.markReaderIndex();
        MsgType msgType = MsgType.values()[in.readInt()];
        int len = in.readInt();

        if (in.readableBytes() < len) {
            in.resetReaderIndex();
            return;
        }

        // 读取消息
        byte[] bytes = new byte[len];
        in.readBytes(bytes);

        // 根据消息类型创建对应的消息对象
        Class<?> clz = Class.forName(MSG_CLASS_PACKAGE + "." + msgType.name() + MSG_CLASS_SUFFIX);
        Msg msg = (Msg) clz.getDeclaredConstructor().newInstance();
        msg.parse(bytes);
        out.add(msg);
    }
}
