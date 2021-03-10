package com.github.vitalz.jrest.json4img.model.color;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.function.Supplier;

public class WhiteRgbSupplier implements Supplier<Triple<Integer, Integer, Integer>> {

    @Override
    public Triple<Integer, Integer, Integer> get() {
        return new ImmutableTriple<>(255, 255, 255);
    }
}
