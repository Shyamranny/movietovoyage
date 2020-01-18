package com.shyam.movietovoyage.core;

import java.awt.image.BufferedImage;

public class VideoImage {

    private long timestamp;
    private BufferedImage bufferedImage;

    public VideoImage(long timestamp, BufferedImage bufferedImage) {
        this.timestamp = timestamp;
        this.bufferedImage = bufferedImage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
