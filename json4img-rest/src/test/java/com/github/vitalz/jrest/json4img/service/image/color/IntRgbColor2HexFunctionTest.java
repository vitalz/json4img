package com.github.vitalz.jrest.json4img.service.image.color;

import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

public class IntRgbColor2HexFunctionTest {

    @Test
    public void test() {
        IntRgbColor2HexFunction rgb2HexFunction = new IntRgbColor2HexFunction();

        assertEquals("#000000", rgb2HexFunction.apply(new Color(0,0, 0).getRGB()));
        assertEquals("#FF0000", rgb2HexFunction.apply(new Color(255,0, 0).getRGB()));
        assertEquals("#00FF00", rgb2HexFunction.apply(new Color(0,255, 0).getRGB()));
        assertEquals("#0000FF", rgb2HexFunction.apply(new Color(0,0, 255).getRGB()));
        assertEquals("#FFFFFF", rgb2HexFunction.apply(new Color(255,255, 255).getRGB()));

        assertEquals("#6496C8", rgb2HexFunction.apply(new Color(100,150, 200).getRGB()));
        assertEquals("#9664C8", rgb2HexFunction.apply(new Color(150,100, 200).getRGB()));
        assertEquals("#C89664", rgb2HexFunction.apply(new Color(200,150, 100).getRGB()));
    }
}
