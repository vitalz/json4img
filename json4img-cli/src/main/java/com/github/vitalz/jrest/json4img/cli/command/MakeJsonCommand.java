package com.github.vitalz.jrest.json4img.cli.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitalz.jrest.json4img.model.Image;
import com.github.vitalz.jrest.json4img.service.file.ConvertedFileName;
import com.github.vitalz.jrest.json4img.service.image.dto.json.ImageDataFactory;
import io.bootique.cli.Cli;
import io.bootique.command.Command;
import io.bootique.command.CommandOutcome;
import io.bootique.meta.application.CommandMetadata;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public final class MakeJsonCommand implements Command {
    private final static Logger log = LoggerFactory.getLogger(MakeJsonCommand.class);

    @Override
    public CommandMetadata getMetadata() {
        return CommandMetadata.builder("make-json").shortName('j').build();
    }

    @Override
    public CommandOutcome run(Cli cli) {
        log.debug("Current directory is assumed to be: '{}'", new File("").getAbsolutePath().trim());

        String fileName = cli.standaloneArguments().get(0);
        String jsonFileName = new ConvertedFileName().apply(fileName, "json");
        log.debug("Making json from image file '{}' to json file '{}'", fileName, jsonFileName);

        try {
            File jsonFile = new File(jsonFileName);
            Image imageData = new ImageDataFactory().readImage(new File(fileName));
            String json = new ObjectMapper().writeValueAsString(imageData);

            jsonFile.delete();
            FileUtils.writeStringToFile(jsonFile, json);

        } catch (IOException e) {
            log.error("File exception has occurred: {}", e);
            return CommandOutcome.failed(1, e);
        }

        return CommandOutcome.succeeded();
    }

}
