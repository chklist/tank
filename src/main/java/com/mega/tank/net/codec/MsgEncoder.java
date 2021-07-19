package com.mega.tank.net.codec;

import com.mega.tank.net.message.Msg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 消息编码器
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) {
        // 消息类型
        buf.writeInt(msg.getMsgType().ordinal());
        byte[] bytes = msg.toBytes();
        // 消息长度
        buf.writeInt(bytes.length);
        // 消息内容
        buf.writeBytes(bytes);
    }
}
