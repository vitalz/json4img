package com.github.vitalz.jrest.json4img.api;

import ch.qos.logback.core.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitalz.jrest.json4img.model.Image;
import com.github.vitalz.jrest.json4img.model.JsonToImage;
import com.github.vitalz.jrest.json4img.service.file.FileStorage;
import com.github.vitalz.jrest.json4img.service.image.ImageFactory;
import com.github.vitalz.jrest.json4img.service.image.InImagePixels;
import com.github.vitalz.jrest.json4img.service.view.IWindowService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

@Path("/image")
public final class ImageApi {
    private static final Logger log = LoggerFactory.getLogger(ImageApi.class);

    @Inject
    private FileStorage fileStorage;

    @Inject
    private Provider<IWindowService> windowService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response jsonToImage(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonToImage json2Img = objectMapper.readValue(json, JsonToImage.class);
            String toRelativePath = json2Img.getToRelativePath();
            Image model = json2Img.getImage();
            BufferedImage bufferedImage = new ImageFactory().createImage(model.getWidth(), model.getHeight(), Color.decode(model.getBackgroundColor()));
            new InImagePixels(model).pixels()
                                    .forEach(p -> bufferedImage.setRGB(p.getX(), p.getY(), Color.decode(p.getColor()).getRGB()));
            File file = new File(fileStorage.getOutputDir().get(), toRelativePath);
            FileUtil.createMissingParentDirectories(file);
            log.info("Writing an image into a file: {}", file.getAbsolutePath());
            ImageIO.write(bufferedImage, "png", file);
            return Response.ok().build();
        } catch (Throwable t) {
            log.error("Server error has occurred.", t);
            return Response.serverError()
                    .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), t.getLocalizedMessage())
                    .build();
        }

    }

    @POST
    @Path("display")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response display(String json) {
        try {
            JsonToImage json2Img = new ObjectMapper().readValue(json, JsonToImage.class);
            Image model = json2Img.getImage();
            BufferedImage bufferedImage = new ImageFactory().createImage(model.getWidth(), model.getHeight(), Color.decode(model.getBackgroundColor()));
            new InImagePixels(model).pixels()
                                    .forEach(p -> bufferedImage.setRGB(p.getX(), p.getY(), Color.decode(p.getColor()).getRGB()));
            windowService.get().display(bufferedImage);
            return Response.ok().build();
        } catch (Throwable t) {
            log.error("Server error has occurred.", t);
            return Response.serverError()
                    .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), t.getLocalizedMessage())
                    .build();
        }
    }

}
