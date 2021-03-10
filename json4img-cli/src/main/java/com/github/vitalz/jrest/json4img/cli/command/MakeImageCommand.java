package com.github.vitalz.jrest.json4img.cli.command;

import io.bootique.cli.Cli;
import io.bootique.command.Command;
import io.bootique.command.CommandOutcome;
import io.bootique.meta.application.CommandMetadata;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class MakeImageCommand implements Command {
    private final static Logger log = LoggerFactory.getLogger(MakeImageCommand.class);

    @Override
    public CommandMetadata getMetadata() {
        return CommandMetadata.builder("make-image").shortName('i').build();
    }

    @Override
    public CommandOutcome run(Cli cli) {
        log.debug("Current directory is assumed to be: '{}'", new File("").getAbsolutePath().trim());

        String fileName = cli.standaloneArguments().get(0);
        String imageFileName = String.format("%s.png", FilenameUtils.removeExtension(fileName));
        log.debug("Making PNG image from json file '{}' to PNG file '{}'", fileName, imageFileName);

        //try {
            File imageFile = new File(imageFileName);
            imageFile.delete();


        /* } catch (IOException e) {
            log.error("File exception has occurred: {}", e);
            return CommandOutcome.failed(1, e);
        } */

        return CommandOutcome.succeeded();
    }

}
