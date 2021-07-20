package com.mega.tank.net;

import com.mega.tank.net.codec.MsgDecoder;
import com.mega.tank.net.codec.MsgEncoder;
import com.mega.tank.net.handler.ClientMsgHandler;
import com.mega.tank.net.message.Msg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    public static NettyClient INSTANCE = new NettyClient();

    private Channel channel;

    private NettyClient() {
    }

    public void connect(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, false)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new MsgEncoder())
                                    .addLast(new MsgDecoder())
                                    .addLast(new ClientMsgHandler());
                        }
                    });

            ChannelFuture cf = b.connect(host, port).sync();
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        logger.info("client connect success");
                        channel = future.channel();
                    }
                }
            });

            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(Msg msg) {
        if (channel != null) {
            channel.writeAndFlush(msg);
        }
    }
}
