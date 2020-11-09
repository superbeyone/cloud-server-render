package com.tdt.cloud.render.utils;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author superbeyone
 */
public class FileUtil {

    public static File generateImage(String base64, String path) throws IOException {
        File file = new File(path);
        return generateImage(base64, file);
    }

    public static File generateImage(String base64, File file) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        System.out.println("file = " + file);
        try (OutputStream out = new FileOutputStream(file)) {
            // 解密
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out.write(b);
            out.flush();
            return file;
        }
    }

    public static String getPath(String path, String dir) {
        String file = path + File.separator + dir + File.separator +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + ".png";

        return file;
    }


}