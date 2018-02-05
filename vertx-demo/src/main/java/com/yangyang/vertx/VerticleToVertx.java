package com.yangyang.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

/**
 * @author chenshunyang
 * @create 2018-01-11 17:27
 **/
public class VerticleToVertx {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MyVerticle());
    }
}

class MyVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> startFuture) {
        System.out.println("MyVerticle started!");
    }

    @Override
    public void stop(Future stopFuture) throws Exception {
        System.out.println("MyVerticle stopped!");
    }
}

