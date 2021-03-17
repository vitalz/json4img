package com.github.vitalz.jrest.json4img.service.image;

import com.github.vitalz.jrest.json4img.model.Image;
import com.github.vitalz.jrest.json4img.model.Pixel;

import java.util.List;
import java.util.stream.Collectors;

public final class InImagePixels {
    private final Image model;

    public InImagePixels(Image model) {
        this.model = model;
    }

    public List<Pixel> pixels() {
        return  model.getPixels()
                        .stream()
                        .filter(new PixelPredicate(model.getWidth(), model.getHeight()))
                        .collect(Collectors.toList());
    }
}
