package com.github.vitalz.jrest.json4img.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitalz.jrest.json4img.model.Image;
import com.github.vitalz.jrest.json4img.model.ImageToJson;
import com.github.vitalz.jrest.json4img.model.Pixel;
import com.github.vitalz.jrest.json4img.service.file.FileStorage;
import com.github.vitalz.jrest.json4img.service.image.color.IntRgbColor2HexFunction;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Path("/api/json")
public class JsonApi {
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

            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file)); // wrap into FileInputSteam to avoid OpenJDK issues
            final int width = bufferedImage.getWidth();
            final int height = bufferedImage.getHeight();

            int backgroundRgb = bufferedImage.getGraphics().getColor().getRGB(); // TODO: need to check if that responds background color in a proper way

            List<Pixel> pixels = new ArrayList(width * height);
            final Function<Integer, String> intRgb2HexColor = new IntRgbColor2HexFunction();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    pixels.add(new Pixel(x, y, intRgb2HexColor.apply(bufferedImage.getRGB(x, y))));
                }
            }

            return Response.ok(
                    new ObjectMapper().writeValueAsString(
                            new Image(width, height, intRgb2HexColor.apply(backgroundRgb), pixels)
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
