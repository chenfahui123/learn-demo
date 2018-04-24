package com.yangyang.wx.service;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WxJSAPICenter {
    /**
     * 	作用：生成可以获得code的url
     */

    public static String createOauthUrlForCode(String redirectUrl){
        String appid = WxJSAPIConfig.MP_APP_ID;
        String redirect_uri= redirectUrl;
        String scope="snsapi_login";
        String response_type = "code";
        String state = "STATE"+"#wechat_redirect";
        String bizString = "appid="+appid+"&redirect_uri="+redirect_uri+"&response_type="+response_type+"&scope="+scope+"&state="+state;
        String url = "https://open.weixin.qq.com/connect/qrconnect?"+bizString;
        System.out.println(url);
        return url;
    }


    public static String getOpenId(String code){

        String appid = WxJSAPIConfig.MP_APP_ID.trim();
        String secret = WxJSAPIConfig.MP_APP_SECRET.trim();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid
                +"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        String result = null;
        try {
            result= doGet(url,result);
        }catch (Exception e){

        }
        return result;
    }

    private static String doGet(String url, String result) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                    result = EntityUtils.toString(entity);
                }
                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static void main(String[] args) throws Exception{
//        createOauthUrlForCode("http://test-iappweb.jpushoa.com/wxdemo/v1/wx/callback");
//		System.out.println("--------------------------");
        System.out.println(getOpenId("081JBQ9f2NTAUA00Pzcf2hN9af2JBQ93"));
    }


}
