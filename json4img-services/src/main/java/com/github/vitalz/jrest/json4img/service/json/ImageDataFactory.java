package com.github.vitalz.jrest.json4img.service.json;

import com.github.vitalz.jrest.json4img.model.Image;
import com.github.vitalz.jrest.json4img.model.Pixel;
import com.github.vitalz.jrest.json4img.model.color.IntRgbColor2HexFunction;

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

public final class ImageDataFactory {
    public static final Logger log = LoggerFactory.getLogger(ImageDataFactory.class);

    public Image readImage(File imageFile) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(imageFile)); // wrap into FileInputSteam to avoid OpenJDK issues
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();

        final Function<Integer, String> intRgb2HexColor = new IntRgbColor2HexFunction();

        final int backgroundRgb = bufferedImage.getGraphics().getColor().getRGB(); // TODO: need to check if that responds background color in a proper way
        log.info("Java AWT recognizes background color: {}", intRgb2HexColor.apply(backgroundRgb));

        List<Pixel> pixels = new ArrayList(width * height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String hexColor = intRgb2HexColor.apply(bufferedImage.getRGB(x, y));
                if (!"#000000".equals(hexColor)) {
                    pixels.add(new Pixel(x, y, hexColor));
                }
            }
        }

        return new Image(width, height, intRgb2HexColor.apply(backgroundRgb), pixels);

    }

}
