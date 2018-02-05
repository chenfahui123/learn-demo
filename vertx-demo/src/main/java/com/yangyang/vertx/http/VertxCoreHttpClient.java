package com.yangyang.vertx.http;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;

/**
 * @author chenshunyang
 * @create 2018-01-10 20:47
 **/
public class VertxCoreHttpClient {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        //使用默认选项创建HttpClient
//        HttpClient client = vertx.createHttpClient();

        //使用配置参数创建httpclient
        HttpClientOptions options = new HttpClientOptions().setKeepAlive(false);
        HttpClient client = vertx.createHttpClient(options);

        client.request(HttpMethod.GET,"http://localhost:8080", response->{
            System.out.println("reponse code:"+response.statusCode());
        });
    }
}
