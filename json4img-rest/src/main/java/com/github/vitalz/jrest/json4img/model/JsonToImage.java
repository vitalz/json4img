package com.github.vitalz.jrest.json4img.model;

public final class JsonToImage {
    private String toRelativePath;
    private Image image;


    public JsonToImage() {
    }

    public String getToRelativePath() {
        return toRelativePath;
    }

    public Image getImage() {
        return image;
    }

    // setters for json serialization
    public void setToRelativePath(String toRelativePath) {
        this.toRelativePath = toRelativePath;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    // end setters
}
