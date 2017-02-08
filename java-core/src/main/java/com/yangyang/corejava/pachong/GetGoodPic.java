/**
 * Copyright 2014-2016 www.fangdd.com All Rights Reserved.
 * Author: Chen Shunyang <chenshunyang@fangdd.com>
 * Date:   2015年9月25日
 */
package com.yangyang.corejava.pachong;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class GetGoodPic {
    // 糗事百科图片贴地址
    public static final String URL = "http://www.qiushibaike.com/pic/page/";
    // 更新参数
    public static final String PARAM = "?s=4490316";
    // 模仿UA
    public static final String UA = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.47 Safari/536.11";
    // 匹配属性名
    public static final String MATCH_ATTR_NAME = "alt";
    // 匹配属性前缀
    public static final String MATCH_ATTR_PREFIX = "糗事#";
    // div节点选择器
    public static final String GOOD_IMG_DIV_SELECTOR = ".untagged";
        public static final int MAX_PAGE = 100;
    // 最低顶次数
    public static final int MIN_UP = 50;
    // 存储路径
    public static final String BASE_PATH = "C:\\糗事百科图片";

    public static void main(String[] args) {

        for (int i = 2; i <= MAX_PAGE; i++) {
            String url = URL + i + PARAM;
            System.out.println("解析URL(第" + i + "页):" + url);

            String result = getResultByUrl(url);

            List<String> urls = getGoodImgUrl(result);
            for (String str : urls) {
                System.out.println(str);
                File img_file = getStoreFile(str);
                if (saveImg(str, img_file))
                    System.out.println("存入图片" + img_file.getName());
            }
        }
    }

    /**
     * 给定url获取内容
     *
     * @param url
     * @return
     */
    public static String getResultByUrl(String url) {
        HttpClient hc = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("User-Agent", UA);
            HttpResponse response = hc.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                StringWriter sw = new StringWriter();
                IOUtils.copy(in, sw);
                in.close();
                return sw.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> getGoodImgUrl(String str) {
        List<String> img_urls = new ArrayList<String>();
        Document doc = Jsoup.parse(str);

        Elements es = doc.select(GOOD_IMG_DIV_SELECTOR);
        for (Iterator<Element> i = es.iterator(); i.hasNext();) {
            Element e = i.next();
            Element[] a_s = new Element[1];
            Element up_a = e.select(".bar .up a").toArray(a_s)[0];
            Element[] p_s = new Element[1];
            Element up_a1 = up_a.select(".number").toArray(p_s)[0];
            System.out.println(up_a1);
            Element[] i_s = new Element[1];
            Element img = e.select(".thumb img").toArray(i_s)[0];
            if (up_a1.text() != null && MIN_UP <= Integer.parseInt(up_a1.text())) {
                img_urls.add(img.attr("src"));
            }
        }
        return img_urls;
    }

    /**
     * 从页面中找出图片的地址
     *
     * @param str
     * @return
     */
    public static List<String> getImgUrlByRes(String str) {
        List<String> img_urls = new ArrayList<String>();
        Document doc = Jsoup.parse(str);

        Elements es = doc.getElementsByAttributeValueStarting(MATCH_ATTR_NAME,
                MATCH_ATTR_PREFIX);

        for (Iterator<Element> i = es.iterator(); i.hasNext();) {
            Element e = i.next();
            img_urls.add(e.attr("src"));
        }
        return img_urls;
    }

    /**
     * 将图片写入本地
     */
    public static boolean saveImg(String img_url, File file) {
        HttpClient hc = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(img_url);
            httpget.setHeader("User-Agent", UA);
            HttpResponse response = hc.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                OutputStream os = new FileOutputStream(file);
                int count = IOUtils.copy(in, os);
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(os);
                if (0 != count)
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成存储路径
     *
     * @param url
     * @return
     */
    public static File getStoreFile(String url) {

        Calendar c = Calendar.getInstance();
        String[] tmp = url.split("/");

        String file_name = tmp[tmp.length - 1];

        String path = BASE_PATH + File.separator + (c.get(Calendar.MONTH)+1)
                + c.get(Calendar.DAY_OF_MONTH) + File.separator
                + c.get(Calendar.HOUR_OF_DAY) + File.separator;
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        File img_file = new File(path + file_name);
        if (!img_file.exists()) {
            try {
                img_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img_file;
    }
}

