package com.yangyang.qrcode;

/**
 * @author chenshunyang
 * @create 2018-04-23 15:33
 **/
public class QrCodeTest {

    public static void main(String[] args) throws Exception {
        /**
         *    QRcode 二维码生成测试
         */
        QRCodeUtil.QRCodeCreate("http://www.baidu.com", "/Users/chenshunyang/code/study/learn-demo/files/qrcode.jpg", 15, "/Users/chenshunyang/code/study/learn-demo/files/icon.png");
        /**
         *     QRcode 二维码解析测试
         */
        String qrcodeAnalyze = QRCodeUtil.QRCodeAnalyze("/Users/chenshunyang/code/study/learn-demo/files/qrcode.jpg");

        /**
         * ZXingCode 二维码生成测试
         */
        QRCodeUtil.zxingCodeCreate("http://www.baidu.com", 300, 300, "/Users/chenshunyang/code/study/learn-demo/files/zxingcode.jpg", "jpg");

        /**
         * ZxingCode 二维码解析
         */
        String zxingAnalyze =  QRCodeUtil.zxingCodeAnalyze("/Users/chenshunyang/code/study/learn-demo/files/zxingcode.jpg").toString();
        System.out.println("success");
    }
}
