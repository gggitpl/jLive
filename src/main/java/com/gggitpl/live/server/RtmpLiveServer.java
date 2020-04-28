package com.gggitpl.live.server;

import com.gggitpl.live.rtmp.RtmpDecoder;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gggitpl
 */
@Slf4j
public class RtmpLiveServer implements LiveServer {

    private final ServerBootstrap bootstrap;
    private final EventLoopGroup boosGroup;
    private final EventLoopGroup workerGroup;
    Channel channel;

    @Inject
    public RtmpLiveServer(ServerBootstrap bootstrap, @Named("bossGroup") EventLoopGroup boosGroup, @Named("workerGroup") EventLoopGroup workerGroup) {
        this.bootstrap = bootstrap;
        this.boosGroup = boosGroup;
        this.workerGroup = workerGroup;
    }

    @Override
    public void start() {
        bootstrap.handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioServerSocketChannel.class)
                .group(boosGroup, workerGroup)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new LoggingHandler(LogLevel.DEBUG))
                                .addLast(new RtmpDecoder());

                    }
                })
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            channel = bootstrap
                    .bind(1935)
                    .sync()
                    .addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            log.info("RTMP Server started successfully");
                        }
                    })
                    .channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("RTMP Server started failure: {}", e.getMessage(), e);
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void stop() {
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("RTMP Server stop failure: {}", e.getMessage(), e);
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
