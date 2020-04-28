package com.gggitpl.live.server;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;


/**
 * @author gggitpl
 */
public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ServerBootstrap.class).toProvider(ServerBootstrap::new);
        bind(LiveServer.class).annotatedWith(Names.named("rtmpLiveServer")).to(RtmpLiveServer.class);
    }

    @Named("bossGroup")
    @Provides
    public EventLoopGroup boosGroup() {
        return new NioEventLoopGroup(1, new DefaultThreadFactory("Live Server Boss Thread"));
    }

    @Named("workerGroup")
    @Provides
    public EventLoopGroup workerGroup() {
        return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new DefaultThreadFactory("Live Server Worker Thread"));
    }
}
