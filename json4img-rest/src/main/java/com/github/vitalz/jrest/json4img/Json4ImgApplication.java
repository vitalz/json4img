package com.github.vitalz.jrest.json4img;

import com.google.inject.Module;
import io.bootique.Bootique;
import io.bootique.jersey.JerseyModule;

public class Json4ImgApplication {

    public static void main(String[] args) {

        Module module = binder -> {
            JerseyModule.extend(binder).addResource(Json4ImgApi.class);
        };

        Bootique
                .app("")
                .module(module)
                .autoLoadModules()
                .exec()
                .exit();
    }

}