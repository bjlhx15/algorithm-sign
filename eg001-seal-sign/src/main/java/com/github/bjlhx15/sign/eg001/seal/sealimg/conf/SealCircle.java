package com.github.bjlhx15.sign.eg001.seal.sealimg.conf;

/**
 * @author lihongxu6
 * @version 1.0
 * @className SealCircle
 * @description TODO
 * @date 2021-01-20 00:00
 */
public class SealCircle {
    public SealCircle(Integer lineSize, Integer width,Integer height) {
        this.lineSize = lineSize;
        this.width = width;
        this.height = height;
    }

    /**
     * 线宽度
     */
    private Integer lineSize;
    /**
     * 半径
     */
    private Integer width;
    /**
     * 半径
     */
    private Integer height;

    public Integer getLineSize() {
        return lineSize;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }
}
