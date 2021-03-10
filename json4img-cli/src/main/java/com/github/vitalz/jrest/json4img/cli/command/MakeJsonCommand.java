package com.github.vitalz.jrest.json4img.cli.command;

import io.bootique.cli.Cli;
import io.bootique.command.Command;
import io.bootique.command.CommandOutcome;
import io.bootique.meta.application.CommandMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MakeJsonCommand implements Command {
    private final static Logger log = LoggerFactory.getLogger(MakeJsonCommand.class);

    @Override
    public CommandMetadata getMetadata() {
        return CommandMetadata.builder("make-json").shortName('j').build();
    }

    @Override
    public CommandOutcome run(Cli cli) {
        String fileName = cli.standaloneArguments().get(0);
        log.debug("Running {}", fileName);
        return CommandOutcome.succeeded();
    }

}
