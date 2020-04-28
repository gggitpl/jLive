package com.gggitpl.live;

import com.gggitpl.live.server.LiveServer;
import com.gggitpl.live.server.ServerModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

/**
 * live server
 */
public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        final LiveServer rtmpLiveServer = injector.getBinding(Key.get(LiveServer.class, Names.named("rtmpLiveServer"))).getProvider().get();
        rtmpLiveServer.start();
    }

    static class AppModule extends AbstractModule {

        @Override
        protected void configure() {
            install(new ServerModule());
        }
    }
}
