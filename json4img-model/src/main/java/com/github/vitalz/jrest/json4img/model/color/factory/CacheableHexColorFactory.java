package com.github.vitalz.jrest.json4img.model.color.factory;

import com.github.vitalz.jrest.json4img.model.color.IntRgbColor2HexFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CacheableHexColorFactory {
    private  final Map<Integer, String> colors = new HashMap<>();
    private final Function<Integer, String> intRgb2HexColor = new IntRgbColor2HexFunction();

    public String hexColor(int color) {
        return colors.computeIfAbsent(color, k -> intRgb2HexColor.apply(color));
    }
}
