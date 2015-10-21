package com.github.nettybook.ch4;

import com.github.nettybook.ch1.EchoServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

//현재 LogLevel.INFO 일때는 뭔가 찍히지만
//LogLevel.DEBUG 일때는 아무것도 찍히지 않으며
// /NettyExample/src/main/com/github/nettybook/ch0/LoggingHandler.java 
// 를 사용하여 구동하면 INFO일때도 아무것도 뜨지않는다.
public class EchoServerV4 {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new EchoServerV4FirstHandler());
                    p.addLast(new EchoServerV4SecondHandler());
                }
           });

            ChannelFuture f = b.bind(8888).sync();

            f.channel().closeFuture().sync();
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}