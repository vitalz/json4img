package com.github.vitalz.jrest.json4img.model;

import java.util.List;

public final class Image {
    private int width;
    private int height;
    private List<Pixel> pixels;

    // default crot for json serialization
    public Image() {}

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

    // setters for json serialization
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPixels(List<Pixel> pixels) {
        this.pixels = pixels;
    }
    // end setters
}
