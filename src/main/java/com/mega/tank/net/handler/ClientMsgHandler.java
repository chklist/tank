package com.mega.tank.net.handler;

import com.mega.tank.GameModel;
import com.mega.tank.net.message.Msg;
import com.mega.tank.net.message.TankJoinMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientMsgHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(new TankJoinMsg(GameModel.INSTANCE.getMainTank()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println(msg);
        msg.handle();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
