package com.github.vitalz.jrest.json4img.cli.command;

import io.bootique.cli.Cli;
import io.bootique.command.Command;
import io.bootique.command.CommandOutcome;
import io.bootique.meta.application.CommandMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MakeImageCommand implements Command {
    private final static Logger log = LoggerFactory.getLogger(MakeImageCommand.class);

    @Override
    public CommandMetadata getMetadata() {
        return CommandMetadata.builder("make-image").shortName('i').build();
    }

    @Override
    public CommandOutcome run(Cli cli) {
        String fileName = cli.standaloneArguments().get(0);
        log.debug("Making image for a json file '{}'", fileName);

        return CommandOutcome.succeeded();
    }

}
