package com.github.vitalz.jrest.json4img;

import io.bootique.Bootique;
import io.bootique.jersey.JerseyModule;

public class Json4ImgApplication {

    public static void main(String[] args) {

        Bootique
                .app(args)
                .autoLoadModules()
                .module(binder -> JerseyModule.extend(binder).addResource(Json4ImgApi.class))
                .exec()
                .exit();
    }

}