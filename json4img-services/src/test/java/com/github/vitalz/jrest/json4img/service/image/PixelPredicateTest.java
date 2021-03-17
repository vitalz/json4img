package com.github.vitalz.jrest.json4img.service.image;

import com.github.vitalz.jrest.json4img.model.Pixel;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

public class PixelPredicateTest {

    @Test
    public void test() {

        PixelPredicate p = new PixelPredicate(2, 2);
        String color = "#FFFFFF";

        // pixels on image
        assertEquals(true, p.test(new Pixel(0, 0, color)));
        assertEquals(true, p.test(new Pixel(1, 0, color)));
        assertEquals(true, p.test(new Pixel(1, 0, color)));
        assertEquals(true, p.test(new Pixel(1, 1, color)));

        // pixels out of image
        // y = -1
        assertEquals(false, p.test(new Pixel(-1, -1, color)));
        assertEquals(false, p.test(new Pixel(0, -1, color)));
        assertEquals(false, p.test(new Pixel(1, -1, color)));
        assertEquals(false, p.test(new Pixel(2, -1, color)));
        // y = 0
        assertEquals(false, p.test(new Pixel(-1, 0, color)));
        assertEquals(false, p.test(new Pixel(2, 0, color)));
        // y = 1
        assertEquals(false, p.test(new Pixel(-1, 1, color)));
        assertEquals(false, p.test(new Pixel(2, 1, color)));
        // y = 2
        assertEquals(false, p.test(new Pixel(-1, 2, color)));
        assertEquals(false, p.test(new Pixel(0, 2, color)));
        assertEquals(false, p.test(new Pixel(1, 2, color)));
        assertEquals(false, p.test(new Pixel(2, 2, color)));

    }
}
