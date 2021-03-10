package com.github.vitalz.jrest.json4img.model.color;

import java.util.function.Function;

public class IntRgbColor2HexFunction implements Function<Integer, String> {

    @Override
    public String apply(Integer intRgbColor) {
        return String.format("#%06X", (0xFFFFFF & intRgbColor));
    }
}
