package com.hankutech.ax.centralserver;

import cn.hutool.core.lang.Assert;
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


    Integer getLastWordLength(String allString) {
        if (allString == null || allString.length() == 0 || allString.trim().length() == 0) {
            return 0;
        }

        int len = allString.length();
        int result = 0;

        for (int i = len - 1; i >= 0; i--) {
            char c = allString.charAt(i);
            System.out.println("index:" + i + ",char:" + c);
            if (c != ' ') {
                result++;
            } else {
                if (result != 0) {
                    System.out.println("break with result="+result);
                    break;
                }
            }
        }
        return result;
    }

    @Test
    void getLastWordLengthTest() {
        Integer len = getLastWordLength("hello nowcoder");
        Assert.isTrue(len == 8);

          len = getLastWordLength("hello nowcoder    ");
        Assert.isTrue(len == 8);

        len = getLastWordLength("  hello     ");
        Assert.isTrue(len == 5);

        len = getLastWordLength("       ");
        Assert.isTrue(len == 0);

        len = getLastWordLength("     a  ");
        Assert.isTrue(len == 1);
        len = getLastWordLength("a       ");
        Assert.isTrue(len == 1);
        len = getLastWordLength("a");
        Assert.isTrue(len == 1);
        len = getLastWordLength("");
        Assert.isTrue(len == 0);
        len = getLastWordLength(" a");
        Assert.isTrue(len == 1);
    }
}
