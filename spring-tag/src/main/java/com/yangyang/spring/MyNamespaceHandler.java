package com.yangyang.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 处理的类 NamespaceHandlerSupport，这个类主要用来调用解释类
 * @author chenshunyang
 * @create 2017-12-26 19:26
 **/
public class MyNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
    }
}
