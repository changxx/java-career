package com.changxx.practice.io;

import com.jd.image.common.ImageUpload;

import java.io.File;

/**
 * @author changxx on 15-9-22.
 */
public class ImgIo {

    public static void main(String[] args) throws Exception {
        File file = new File("/home/changxx/Downloads/560127feNb8ddbe13.png");
        String string = ImageUpload.uploadFile(file, "11a690dd03c6c048a3098730dab812e2");
        System.out.println(string);
    }

}
