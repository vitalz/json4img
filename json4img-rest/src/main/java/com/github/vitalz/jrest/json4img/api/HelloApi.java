package com.github.vitalz.jrest.json4img.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public final class HelloApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        return "{\"message\":\"Json for Image works!\"}";
    }

}
