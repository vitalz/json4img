package com.github.vitalz.jrest.json4img;

import com.google.inject.Binder;
import com.google.inject.Module;
import io.bootique.BQCoreModule;
import io.bootique.Bootique;
import io.bootique.jersey.JerseyModule;
import io.bootique.meta.application.OptionMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Json4ImgApplication implements Module {
    private final static Logger log = LoggerFactory.getLogger(Json4ImgApplication.class);

    public static void main(String[] args) {
        Bootique.app(args)
                .autoLoadModules()
                .module(Json4ImgApplication.class)
                .exec()
                .exit();
    }

    @Override
    public void configure(Binder binder) {

        BQCoreModule.extend(binder)
                .addConfig("classpath:com/github/vitalz/jrest/json4img/server.yml")
                .addOption(OptionMetadata.builder("local").build())
                .mapConfigResource("local", "classpath:com/github/vitalz/jrest/json4img/server.yml");

        JerseyModule.extend(binder)
                .addResource(Json4ImgApi.class);
    }
}