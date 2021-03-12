package com.github.vitalz.jrest.json4img.model.color.factory;

import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

public class CacheableHexColorFactoryTest {

    @Test
    public void test() {

        CacheableHexColorFactory colors = new CacheableHexColorFactory();

        final int color = new Color(119, 136, 153).getRGB();

        String hex1 = colors.hexColor(color);

        assertEquals(true, hex1.equals("#778899"));
        assertEquals(true, hex1 == colors.hexColor(color));
    }

}
