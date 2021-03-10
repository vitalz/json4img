package com.github.vitalz.jrest.json4img.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitalz.jrest.json4img.model.ImageToJson;
import com.github.vitalz.jrest.json4img.service.file.FileStorage;
import com.github.vitalz.jrest.json4img.service.json.ImageDataFactory;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

@Path("/json")
public final class JsonApi {
    private static final Logger log = LoggerFactory.getLogger(JsonApi.class);

    @Inject
    private FileStorage fileStorage;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response imageToJson(String imageToJson) throws IOException {

        ImageToJson jsonParams = new ObjectMapper().readValue(imageToJson, ImageToJson.class);
        String path = jsonParams.getPath();

        log.debug("Requested for an image file on a path: {}", path);

        File file = new File(fileStorage.getSharedDir().get(), path);
        if (!file.exists() || file.isDirectory()) {
            log.info("No file on a path requested: {}", file.getAbsolutePath());
            return Response.serverError()
                    .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Requested path is not a file.")
                    .build();
        }

        try {
            return Response.ok(
                    new ObjectMapper().writeValueAsString(new ImageDataFactory().readImage(file))
            ).build();

        } catch (Throwable t) {
            log.error("Server exception has occurred.\n", t);
            return Response.serverError()
                    .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), t.getLocalizedMessage())
                    .build();
        }

    }
}
