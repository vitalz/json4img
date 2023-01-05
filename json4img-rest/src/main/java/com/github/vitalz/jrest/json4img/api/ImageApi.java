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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.util.function.Supplier;

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
            JsonToImage json2Img = new ObjectMapper().readValue(json, JsonToImage.class);
            Image model = json2Img.getImage();
            BufferedImage bufferedImage = new ImageFactory().createImage(model.getWidth(), model.getHeight(), Color.decode(model.getBackgroundColor()));
            new InImagePixels(model).pixels()
                                    .forEach(p -> bufferedImage.setRGB(p.getX(), p.getY(), Color.decode(p.getColor()).getRGB()));
            File file = new File(fileStorage.getOutputDir().get(), json2Img.getToRelativePath());
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

    @GET
    @Path("display/test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response displayTestDemo() {
        try {
            Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) screenDimension.getWidth();
            int height = (int) screenDimension.getHeight();
            BufferedImage bufferedImage = new ImageFactory().createImage(width, height, Color.WHITE);

            Random random = new Random();
            Supplier<Integer> getRandomRgb = () -> random.nextInt(255 + 1);
            Supplier<Boolean> getIsToFill = () -> random.nextInt(100 + 1) < 20;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (getIsToFill.get()) {
                        bufferedImage.setRGB(x, y, new Color(getRandomRgb.get(), getRandomRgb.get(), getRandomRgb.get()).getRGB());
                    }
                }
            }

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
