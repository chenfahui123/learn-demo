package com.yangyang.vertx.router;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

/**
 * @author chenshunyang
 * @create 2018-01-22 20:40
 **/
public class VertxHttpHandlerDefinition {
    private String path;
    private HttpMethod[] method;
    private Handler<RoutingContext> handler;

    public VertxHttpHandlerDefinition(String path, HttpMethod[] method, Handler<RoutingContext> handler) {
        this.path = path;
        this.method = method;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod[] getMethod() {
        return method;
    }

    public Handler<RoutingContext> getHandler() {
        return handler;
    }
}

