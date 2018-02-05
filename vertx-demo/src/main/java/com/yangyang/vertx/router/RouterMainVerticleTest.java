package com.yangyang.vertx.router;

import io.vertx.core.*;
import io.vertx.core.http.*;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author chenshunyang
 * @create 2018-01-10 19:23
 **/
public class RouterMainVerticleTest extends AbstractVerticle{
    public static void main(String[] args) {
        // Vert.x实例是vert.x api的入口点，我们调用vert.x中的核心服务时，均要先获取vert.x实例，
        // 通过该实例来调用相应的服务，例如部署verticle、创建http server
        VertxOptions options = new VertxOptions();
        Vertx vertx = Vertx.vertx(options);
        vertx.deployVerticle(RouterMainVerticleTest.class.getName());
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        final Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route(HttpMethod.GET,"/api").handler(new ForwardRequestHandler());


//        VertxHttpHandlerDefinition httpHandlerDefinition = new VertxHttpHandlerDefinition("/abc",new HttpMethod[]{HttpMethod.GET},new ForwardRequestHandler());
//        for (HttpMethod method : httpHandlerDefinition.getMethod()) {
//            router.route(method,httpHandlerDefinition.getPath()).handler(httpHandlerDefinition.getHandler());
//        }

        // router.get("/hello")表示所监听URL路径
        router.get("/hello").handler(new Handler<RoutingContext>() {
            public void handle(RoutingContext routingContext) {
                routingContext.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").end("Hello World");
            }
        });



        HttpServerOptions options = new HttpServerOptions();
        HttpServer server = vertx.createHttpServer();
        // 传递方法引用，监听端口
        server.requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest event) {
                router.accept(event);
            }
        }).listen(8080,res->{
            if (res.succeeded()){
                System.out.println("server now is listening");
            }else {
                System.out.println("falied to listen");
            }
        });// 监听端口号
    }

    @Override
    public void stop(Future stopFuture) throws Exception {
        System.out.println("MainVerticle stopped!");
    }

}
