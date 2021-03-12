package com.github.vitalz.jrest.json4img.service.image.model;

import com.github.vitalz.jrest.json4img.model.Image;
import com.github.vitalz.jrest.json4img.model.Pixel;
import com.github.vitalz.jrest.json4img.model.color.IntRgbColor2HexFunction;

import com.github.vitalz.jrest.json4img.model.color.factory.CacheableHexColorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class ImageModelFactory {
    public static final Logger log = LoggerFactory.getLogger(ImageModelFactory.class);

    public Image readImage(File imageFile) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(imageFile)); // wrap into FileInputSteam to avoid OpenJDK issues
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        log.info("Image size detected is: {} x {}. That means there are {} pixels.", height, width, String.format("%,d", height * width));

        final Function<Integer, String> intRgb2HexColor = new IntRgbColor2HexFunction();
        final CacheableHexColorFactory hexColors = new CacheableHexColorFactory();

        final int backgroundRgb = bufferedImage.getGraphics().getColor().getRGB(); // TODO: need to check if that responds background color in a proper way
        final String hexBackground = intRgb2HexColor.apply(backgroundRgb);
        log.info("Java AWT has recognized background color: {}.", hexBackground);

        List<Pixel> pixels = new ArrayList(width * height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String hexColor = hexColors.hexColor(bufferedImage.getRGB(x, y));
                if (!hexBackground.equals(hexColor)) {
                    pixels.add(new Pixel(x, y, hexColor));
                }
            }
        }
        log.info("There are {} colors have been detected.", String.format("%,d",hexColors.cacheSize()));

        return new Image(width, height, hexBackground, pixels);

    }

}
