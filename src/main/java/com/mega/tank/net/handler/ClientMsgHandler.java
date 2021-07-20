package com.mega.tank.net.handler;

import com.mega.tank.GameModel;
import com.mega.tank.net.message.Msg;
import com.mega.tank.net.message.TankJoinMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMsgHandler extends SimpleChannelInboundHandler<Msg> {

    private static final Logger logger = LoggerFactory.getLogger(ClientMsgHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(new TankJoinMsg(GameModel.INSTANCE.getMainTank()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        logger.info("message = {}", msg);
        msg.handle();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
        ctx.close();
    }
}
