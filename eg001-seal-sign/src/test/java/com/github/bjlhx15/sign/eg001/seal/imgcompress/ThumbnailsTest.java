package com.github.bjlhx15.sign.eg001.seal.imgcompress;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author lihongxu6
 * @version 1.0
 * @className CompressImgTest
 * @description TODO
 * @date 2021-01-23 10:03
 */
public class ThumbnailsTest {

    @Test
    public void handle() throws Exception {

        String inputPath = "src/main/resources/WechatIMG2.jpeg";
        String outputPathPrefix = "src/main/resources/img/WechatIMG_";
        String watermark = "src/main/resources/watermark.png";

        /**
         * size(width,height) 指定大小进行缩放，若图片横比200小，高比300小，不变
         * 若图片横比200小，高比300大，高缩小到300，图片比例不变 若图片横比200大，高比300小，横缩小到200，图片比例不变
         * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
         */
        Thumbnails.of(inputPath).size(200, 300).toFile(outputPathPrefix + "200x300.jpg");
        Thumbnails.of(inputPath).size(2560, 2048).toFile(outputPathPrefix + "2560x2048.jpg");

        /**
         * scale(比例):按照比例进行缩放
         */
        Thumbnails.of(inputPath).scale(0.25f).toFile(outputPathPrefix + "25%.jpg");
        Thumbnails.of(inputPath).scale(1.10f).toFile(outputPathPrefix + "110%.jpg");

        /**
         * 不按照比例，指定大小进行缩放 设为false
         * keepAspectRatio(true) 默认是按照比例缩放的
         */
        Thumbnails.of(inputPath).size(120, 120).keepAspectRatio(false).toFile(outputPathPrefix + "120x120.jpg");

        /**
         * rotate(角度),旋转，正数：顺时针 负数：逆时针
         */
        Thumbnails.of(inputPath).size(1280, 1024).rotate(90).toFile(outputPathPrefix+"+90.jpg");
        Thumbnails.of(inputPath).size(1280, 1024).rotate(-90).toFile(outputPathPrefix+"-90.jpg");

        /**
         * watermark 水印 (位置，水印图，透明度)
         */
        Thumbnails.of(inputPath).size(1280, 1024)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(watermark)), 0.5f)
                .outputQuality(0.8f).toFile(outputPathPrefix + "watermark_bottom_right.jpg");
        Thumbnails.of(inputPath).size(1280, 1024)
                .watermark(Positions.CENTER, ImageIO.read(new File(watermark)), 0.5f)
                .outputQuality(0.8f).toFile(outputPathPrefix + "watermark_center.jpg");

        /**
         * 裁剪
         */
        // 图片中心400*400的区域
        Thumbnails.of(inputPath).sourceRegion(Positions.CENTER, 400, 400).size(200, 200).keepAspectRatio(false)
                .toFile(outputPathPrefix + "region_center.jpg");
        //图片右下400*400的区域
        Thumbnails.of(inputPath).sourceRegion(Positions.BOTTOM_RIGHT, 400, 400).size(200, 200).keepAspectRatio(false)
                .toFile(outputPathPrefix + "region_bootom_right.jpg");
        //指定坐标
        Thumbnails.of(inputPath).sourceRegion(600, 500, 400, 400).size(200, 200).keepAspectRatio(false)
                .toFile(outputPathPrefix + "region_coord.jpg");

        /**
         * outputFormat 转化图像格式(图像格式)
         */
        Thumbnails.of(inputPath).size(1280, 1024).outputFormat("png").toFile(outputPathPrefix + "1280x1024.png");
        Thumbnails.of(inputPath).size(1280, 1024).outputFormat("gif").toFile(outputPathPrefix + "1280x1024.gif");

        /**
         * toOutputStream 输出到OutputStream(流对象)
         */
        OutputStream os = new FileOutputStream(outputPathPrefix + "1280x1024_OutputStream.png");
        Thumbnails.of(inputPath).size(1280, 1024).toOutputStream(os);

        /**
         * asBufferedImage() 输出到BufferedImage,返回BufferedImage
         */
        BufferedImage thumbnail = Thumbnails.of(inputPath).size(1280, 1024).asBufferedImage();
        ImageIO.write(thumbnail, "jpg", new File(outputPathPrefix + "1280x1024_BufferedImage.jpg"));
    }

}