package com.github.vitalz.jrest.json4img;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;

import ch.qos.logback.core.util.FileUtil;
import com.github.vitalz.jrest.json4img.model.JsonToImage;
import com.github.vitalz.jrest.json4img.service.file.FileStorage;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.vitalz.jrest.json4img.model.Image;
import com.github.vitalz.jrest.json4img.model.Pixel;


@Path("/api")
public final class Json4ImgApi {
    private static final Logger log = LoggerFactory.getLogger(Json4ImgApi.class);

    @Inject
    private FileStorage fileStorage;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        return "{\"message\":\"Json for Image works!\"}";
    }

    @POST
    @Path("image")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response jsonToImage(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonToImage json2Img = objectMapper.readValue(json, JsonToImage.class);
            String toRelativePath = json2Img.getToRelativePath();
            Image model = json2Img.getImage();
            BufferedImage bufferedImage = new BufferedImage(model.getWidth(), model.getHeight(), BufferedImage.TYPE_INT_RGB);  // TYPE_INT_RGB for OpenJDK
            model.getPixels().forEach(p -> bufferedImage.setRGB(p.getX(), p.getY(), Color.decode(p.getColor()).getRGB()));
            File file = new File(fileStorage.getSharedDir().get(), toRelativePath);
            FileUtil.createMissingParentDirectories(file);
            ImageIO.write(bufferedImage, "png", file);
            return Response.ok().build();
        } catch (Throwable t) {
            log.error("Server error has occurred.", t);
            return Response.serverError()
                    .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), t.getLocalizedMessage())
                    .build();
        }

    }

    @GET
    @Path("json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response imageToJson (@QueryParam("path") String path) {
        log.debug("Requested for an image file on a path: {}", path);

        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            log.info("No file on a path requested: {}", path);
            return Response.serverError()
                    .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Requested path is not a file.")
                    .build();
        }

        try {

            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(path)); // wrap into FileInputSteam to avoid OpenJDK issues
            final int width = bufferedImage.getWidth();
            final int height = bufferedImage.getHeight();

            List<Pixel> pixels = new ArrayList(width * height);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    pixels.add(new Pixel(x, y, String.format("#%06X", (0xFFFFFF & bufferedImage.getRGB(x, y)))));
                }
            }

            return Response.ok(
                    new ObjectMapper().writeValueAsString(
                            new Image(width, height, pixels)
                    )
            ).build();

        } catch (Throwable t) {
            log.error("Server exception has occurred.\n", t);
            return Response.serverError()
                    .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), t.getLocalizedMessage())
                    .build();
        }

    }

}
