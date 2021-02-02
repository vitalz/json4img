package com.github.vitalz.jrest.json4img;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitalz.jrest.json4img.model.Image;
import com.github.vitalz.jrest.json4img.model.Pixel;

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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/j4i")
public class Json4ImgApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        return "{\"message\":\"Json for Image works!\"}";
    }

    @POST
    @Path("j2i")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response jsonToImage(String json) {
        return Response.ok().build();
    }

    @GET
    @Path("i2j")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response imageToJson (@QueryParam("path") String path) throws IOException {

        //BufferedImage bufferedImage = ImageIO.read(new File(path));
        final int width = 1980; //bufferedImage.getWidth();
        final int height = 1080; //bufferedImage.getHeight();

        List<Pixel> pixels = new ArrayList(width * height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels.add(new Pixel(x, y, "#FFFFFF"));
            }
        }

        return Response.ok(
                new ObjectMapper().writeValueAsString(
                        new Image(width, height, pixels)
                )
        ).build();
    }

}
