package com.github.vitalz.jrest.json4img.model;

import com.github.vitalz.jrest.json4img.service.image.color.IntRgbColor2HexFunction;

import java.awt.Color;
import java.util.List;
import java.util.Optional;

public final class Image {
    private int width;
    private int height;
    private String backgroundColor;
    private List<Pixel> pixels;

    // default ctor for json serialization
    public Image() {}

    public Image(int width, int height, List<Pixel> pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public Image(int width, int height, String backgroundColor, List<Pixel> pixels) {
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.pixels = pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getBackgroundColor() {
        return Optional.ofNullable(backgroundColor).orElse(new IntRgbColor2HexFunction().apply(Color.WHITE.getRGB()));
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

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setPixels(List<Pixel> pixels) {
        this.pixels = pixels;
    }
    // end setters
}
