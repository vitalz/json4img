package com.github.vitalz.jrest.json4img.service.file;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConvertedFileNameTest {

    @Test
    public void test() {
        ConvertedFileName converter = new ConvertedFileName();

        assertEquals("M1.txt", converter.apply("M1.json","txt"));
        assertEquals("M1.txt", converter.apply("M1.png","txt"));

        assertEquals("M1.png", converter.apply("M1.json","png"));
        assertEquals("M1.json", converter.apply("M1.png","json"));

        assertEquals("/data/points.csv", converter.apply("/data/points.txt","csv"));
    }

}
