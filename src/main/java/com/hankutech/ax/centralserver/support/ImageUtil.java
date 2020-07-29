package com.hankutech.ax.centralserver.support;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtil {


    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();

    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();


    public static String imageToBase64(String imgFilePath, String imgFormat) {
        File f = new File(imgFilePath);
        try {
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, imgFormat, baos);
            byte[] bytes = baos.toByteArray();

            return encoder.encodeBuffer(bytes).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String base64ToImage(String base64String, String imgFilePath, String imgFormat) {
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            if (null == bytes1 || bytes1.length == 0) {
                return null;
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            if (null == bi1) {
                return null;
            }
            File f1 = new File(imgFilePath);
            ImageIO.write(bi1, imgFormat, f1);
            return imgFilePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
