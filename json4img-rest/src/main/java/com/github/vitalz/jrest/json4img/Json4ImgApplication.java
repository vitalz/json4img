package com.github.vitalz.jrest.json4img;

import com.github.vitalz.jrest.json4img.api.HelloApi;
import com.github.vitalz.jrest.json4img.api.ImageApi;
import com.github.vitalz.jrest.json4img.api.JsonApi;
import com.github.vitalz.jrest.json4img.service.file.FileStorageFactory;
import com.github.vitalz.jrest.json4img.service.file.FileStorage;
import com.github.vitalz.jrest.json4img.service.view.IWindowService;
import com.github.vitalz.jrest.json4img.service.view.WindowService;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.bootique.BQCoreModule;
import io.bootique.Bootique;
import io.bootique.config.ConfigurationFactory;
import io.bootique.jersey.JerseyModule;
import io.bootique.meta.application.OptionMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Json4ImgApplication implements Module {
    private final static Logger log = LoggerFactory.getLogger(Json4ImgApplication.class);

    public static void main(String[] args) {
        log.debug("Running main...");
        Bootique.app(args)
                .autoLoadModules()
                .module(Json4ImgApplication.class)
                .exec()
                .exit();
    }

    @Override
    public void configure(Binder binder) {
        log.debug("Configuring...");

        BQCoreModule.extend(binder)
                .addConfig("classpath:com/github/vitalz/jrest/json4img/server.yml")
                .addOption(OptionMetadata.builder("local").build())
                .mapConfigResource("local", "classpath:com/github/vitalz/jrest/json4img/server.yml")
                .declareVar("fs.sharedDir", "FS_SHAREDDIR") // declare env var name for Bootique framework
                .declareVar("fs.outputDir", "FS_OUTPUTDIR") // declare env var name for Bootique framework
        ;

        binder.bind(IWindowService.class).to(WindowService.class).in(Singleton.class);

        JerseyModule.extend(binder)
                .addResource(HelloApi.class)
                .addResource(JsonApi.class)
                .addResource(ImageApi.class)
        ;

    }

    @Singleton
    @Provides
    public FileStorage provideSharedDirService(ConfigurationFactory configFactory) {
        log.debug("Providing file storage service...");
        return configFactory.config(FileStorageFactory.class, "fs").createFileStorageService();
    }
}