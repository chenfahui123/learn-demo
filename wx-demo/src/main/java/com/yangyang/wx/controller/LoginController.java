package com.yangyang.wx.controller;

import com.yangyang.wx.service.WxJSAPICenter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/v1/wx")
public class LoginController {
    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String redirectUrl="http://test-iappweb.aaa.com/wxdemo/v1/wx/callback";
        String url = WxJSAPICenter.createOauthUrlForCode(redirectUrl);
        System.out.println("url:"+url);
        response.sendRedirect(url);
        return url;
    }

    @RequestMapping(value="/callback",method= RequestMethod.GET)
    public String callback(HttpServletRequest request, HttpServletResponse response){
        String code = (String)request.getAttribute("code");
        System.out.println("code="+code);
        //根据code拿openid
        String openId = WxJSAPICenter.getOpenId(code);
        System.out.println("openID:"+openId);
        return openId;

    }

}
