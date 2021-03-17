package com.github.vitalz.jrest.json4img.service.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageFactory {

    public BufferedImage createImage(int width, int height) {
        return this.createImage(width, height, Color.WHITE); // background color is white by default
    }

    public BufferedImage createImage(int width, int height, Color color) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  // TYPE_INT_RGB for OpenJDK
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setBackground(color);
        g2d.clearRect(0, 0, width, height);
        return bufferedImage;
    }
}
