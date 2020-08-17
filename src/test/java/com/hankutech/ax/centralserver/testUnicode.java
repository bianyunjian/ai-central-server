package com.hankutech.ax.centralserver;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

public class testUnicode {

    @Test
    void convert() throws UnsupportedEncodingException {
        String charsetName = "GB18030";
        //String message = "1";
        String message = "一";
        byte[] byteData = message.getBytes(charsetName);

        for (byte b :
                byteData) {
            System.out.println(b);
        }
        String receivedString = new String(byteData, charsetName);
        System.out.println(receivedString);

        byte[] byteData2 = new byte[]{-46, -69};
        String receivedString2 = new String(byteData2, charsetName);
        System.out.println(receivedString2);


    }

    @Test
    void printZS() {
//        用来打印出 1~100内的质数
        int start = 1;
        int end = 100;
        if (start == 1) {
            start = 2;
        }
        boolean notZS = false;
        for (int i = start; i <= end; i++) {
            notZS = false;
            for (int j = 2; j <= i / 2; j++) {

                if (i % j == 0) {
                    notZS = true;
                    break;
                }
            }
            if (notZS == false) {
                System.out.println(i);
            }
        }
    }
}
