package com.example.baking.Steps;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Step implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("description")
    private String description;
    @SerializedName("videoURL")
    private String videoURL;
    @SerializedName("thumbnailURL")
    private String thumbnailURL;

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
