package com.github.vitalz.jrest.json4img.cli.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitalz.jrest.json4img.model.Image;
import com.github.vitalz.jrest.json4img.service.file.ConvertedFileName;
import com.github.vitalz.jrest.json4img.service.image.ImageFactory;
import com.github.vitalz.jrest.json4img.service.image.PixelPredicate;
import io.bootique.cli.Cli;
import io.bootique.command.Command;
import io.bootique.command.CommandOutcome;
import io.bootique.meta.application.CommandMetadata;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class MakeImageCommand implements Command {
    private final static Logger log = LoggerFactory.getLogger(MakeImageCommand.class);

    @Override
    public CommandMetadata getMetadata() {
        return CommandMetadata.builder("mkimg").shortName('i').build();
    }

    @Override
    public CommandOutcome run(Cli cli) {
        log.debug("Current directory is assumed to be: '{}'", new File("").getAbsolutePath().trim());

        String fileName = cli.standaloneArguments().get(0);
        String imageFileName = new ConvertedFileName().apply(fileName, "png");
        log.debug("Making PNG image from json file '{}' to PNG file '{}'", fileName, imageFileName);

        try {
            File imageFile = new File(imageFileName);
            imageFile.delete();

            String json = FileUtils.readFileToString(new File(fileName));

            Image model = new ObjectMapper().readValue(json, Image.class);
            log.info("Image size detected is {} x {}. And there are {} unique pixels declared on json.", model.getWidth(), model.getHeight(), String.format("%,d", model.getPixels().size()));

            BufferedImage bufferedImage = new ImageFactory().createImage(model.getWidth(), model.getHeight(), Color.decode(model.getBackgroundColor()));
            model.getPixels()
                    .stream()
                    .filter(new PixelPredicate(model.getWidth(), model.getHeight()))
                    .forEach(p -> bufferedImage.setRGB(p.getX(), p.getY(), Color.decode(p.getColor()).getRGB()));

            log.debug("Saving PNG image to a file {}", imageFile.getAbsolutePath());
            ImageIO.write(bufferedImage, "png", imageFile);

        } catch (IOException e) {
            log.error("File exception has occurred: {}", e);
            return CommandOutcome.failed(1, e);
        }

        return CommandOutcome.succeeded();
    }

}
