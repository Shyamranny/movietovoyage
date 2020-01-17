package com.shyam.movietovoyage.core;

public class ExtractRequest {

    private String videoUrl;
    private String pushUrl;
    private int frameDurationInSecond;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public int getFrameDurationInSecond() {
        return frameDurationInSecond;
    }

    public void setFrameDurationInSecond(int frameDurationInSecond) {
        this.frameDurationInSecond = frameDurationInSecond;
    }
}
