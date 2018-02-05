package com.yangyang.vertx.http;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;

/**
 * @author chenshunyang
 * @create 2018-01-10 19:23
 **/
public class VertxCoreHttpServer{
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        //不带参数的http server
        //HttpServer server = vertx.createHttpServer();

        //配置带参数的httpserver
        HttpServerOptions options = new HttpServerOptions().setMaxWebsocketFrameSize(1000000);
        HttpServer server = vertx.createHttpServer(options);

        server.requestHandler(request -> {
        // This handler gets called for each request that arrives on the server
            System.out.println("request url："+request.uri());
            HttpServerResponse response = request.response();
            response.putHeader("content-type", "text/plain");
         // Write to the response and end it
            response.end("Hello World!");
        });
       //直接绑定
//        server.listen(8080);
        //绑定后异步处理状态
        server.listen(8080,res->{
            if (res.succeeded()){
                System.out.println("server is now listening");
            }else {
                System.out.println("Fail to listen");
            }
        });
    }

}
