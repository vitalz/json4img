package com.github.vitalz.jrest.json4img.cli;

import com.github.vitalz.jrest.json4img.cli.command.MakeImageCommand;
import com.github.vitalz.jrest.json4img.cli.command.MakeJsonCommand;
import com.google.inject.Binder;
import com.google.inject.Module;
import io.bootique.BQCoreModule;
import io.bootique.Bootique;
import io.bootique.meta.application.OptionMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CliApplication implements Module {
    private final static Logger log = LoggerFactory.getLogger(CliApplication.class);

    public static void main(String[] args) {
        log.debug("Running main...");
        Bootique.main(args);
    }

    @Override
    public void configure(Binder binder) {
        log.debug("Configuring...");

        BQCoreModule.extend(binder).addCommand(MakeImageCommand.class);
        BQCoreModule.extend(binder).addCommand(MakeJsonCommand.class);

        BQCoreModule.extend(binder)
                .addConfig("classpath:com/github/vitalz/jrest/json4img/cli/default.yml")
                .addOption(OptionMetadata.builder("local").build())
                .mapConfigResource("local", "classpath:com/github/vitalz/jrest/json4img/cli/default.yml");
    }
}
