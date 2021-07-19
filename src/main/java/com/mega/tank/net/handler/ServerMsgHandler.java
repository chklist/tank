package com.mega.tank.net.handler;

import com.mega.tank.net.NettyServer;
import com.mega.tank.net.message.Msg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMsgHandler extends SimpleChannelInboundHandler<Msg> {

    private static final Logger logger = LoggerFactory.getLogger(ServerMsgHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 保存客户端Channel
        NettyServer.clients.add(ctx.channel());
        logger.info("client num: {}", NettyServer.clients.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        logger.info("message = {}", msg);
        // 转发消息给所有客户端
        NettyServer.clients.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn(cause.getMessage());
        NettyServer.clients.remove(ctx.channel());
        logger.info("client num: {}", NettyServer.clients.size());
        ctx.close();
    }
}
