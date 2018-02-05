package com.yangyang.vertx.router;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

/**
 * @author chenshunyang
 * @create 2018-01-22 20:34
 **/
public class ForwardRequestHandler  implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.response().putHeader("Content-Length","1000000").putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").write("world");
        routingContext.response().setChunked(true);
        routingContext.next();
    }
}
