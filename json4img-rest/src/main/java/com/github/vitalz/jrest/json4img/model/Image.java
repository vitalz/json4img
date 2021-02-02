package com.github.vitalz.jrest.json4img.model;

import java.util.List;

public class Image {
    private final int width;
    private final int height;
    private final List<Pixel> pixels;

    public Image(int width, int height, List<Pixel> pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Pixel> getPixels() {
        return pixels;
    }
}
