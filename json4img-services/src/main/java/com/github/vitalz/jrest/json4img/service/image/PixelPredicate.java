package com.github.vitalz.jrest.json4img.service.image;

import com.github.vitalz.jrest.json4img.model.Pixel;

import java.util.function.Predicate;

public final class PixelPredicate implements Predicate<Pixel> {
    private final int width;
    private final int height;

    public PixelPredicate(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean test(Pixel pixel) {
        final int x = pixel.getX();
        final int y = pixel.getY();
        return (x >= 0 && x < width) && (y >=0 && y < height);
    }

}
