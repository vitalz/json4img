package com.github.vitalz.jrest.json4img.service.image;

import com.github.vitalz.jrest.json4img.service.image.color.WhiteRgbSupplier;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageFactory {

    public BufferedImage createImage(int width, int height) {
        return this.createImage(width, height, new WhiteRgbSupplier().get()); // background color is white by default
    }

    public BufferedImage createImage(int width, int height, Triple<Integer, Integer, Integer> rgb) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  // TYPE_INT_RGB for OpenJDK
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setBackground(new Color(rgb.getLeft(), rgb.getMiddle(), rgb.getRight()));
        g2d.clearRect(0, 0, width, height);
        return bufferedImage;
    }
}
