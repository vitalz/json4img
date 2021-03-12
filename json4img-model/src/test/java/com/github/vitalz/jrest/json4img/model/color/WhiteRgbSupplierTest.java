package com.github.vitalz.jrest.json4img.model.color;

import com.github.vitalz.jrest.json4img.model.color.WhiteRgbSupplier;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class WhiteRgbSupplierTest {

    @Test
    public void test() {
        Supplier<Triple<Integer, Integer, Integer>> whiteRgb = new WhiteRgbSupplier();
        assertEquals(new ImmutableTriple<>(255, 255, 255), whiteRgb.get());
    }
}
