package com.shyam.movietovoyage.extractor;

import com.shyam.movietovoyage.core.VideoImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImageExtractor {

    private static final int ONE_MICRO_SECOND = 10000000;
    private static final int DEFAULT_FRAME_DURATION = 10 * ONE_MICRO_SECOND;
    private String videoFile;
    private int frameDuration;

    public ImageExtractor(String videoFile, int frameDuration) {
        this.videoFile = videoFile;
        this.frameDuration = frameDuration + ONE_MICRO_SECOND;
    }

    public List<VideoImage> extract() throws Exception{

        List<VideoImage> list = new ArrayList<>();

        FFmpegFrameGrabber g = new FFmpegFrameGrabber(videoFile);

        g.start();

        long timestamp = frameDuration;

        while (true) {

            g.setTimestamp(timestamp);
            Frame frame = g.grabImage();
            BufferedImage image = new Java2DFrameConverter().convert(frame);
            if (null == image) {
                break;
            }

            VideoImage videoImage = new VideoImage(timestamp, image);
            list.add(videoImage);

            timestamp += frameDuration;

            if (timestamp < g.getLengthInTime() / ONE_MICRO_SECOND) {
                break;
            }
        }

        g.stop();


        return list;

    }

    public void extract(final Subscriber subscriber){

        if (null == subscriber) {
            throw new NullPointerException("Subscriber is null!!");
        }

        if (null == videoFile || !videoFile.isEmpty()) {
            throw new RuntimeException("Video file is null or does not exist");
        }

        if (0 == frameDuration) {
            frameDuration = DEFAULT_FRAME_DURATION;
        }

        new Thread(() -> {

            try(FFmpegFrameGrabber g = new FFmpegFrameGrabber(videoFile)) {

                g.start();

                long timestamp = frameDuration;

                while (true) {

                    g.setTimestamp(timestamp);
                    Frame frame = g.grabImage();
                    BufferedImage image = new Java2DFrameConverter().convert(frame);
                    if (null == image) {
                        break;
                    }
                    subscriber.subscribe(image);

                    timestamp += frameDuration;

                    if (timestamp < g.getLengthInTime() / ONE_MICRO_SECOND) {
                        break;
                    }
                }

                g.stop();
                subscriber.finished();


            } catch (FrameGrabber.Exception e) {
                subscriber.error(e.getMessage(), e);
            }


        }).start();

    }

    public interface Subscriber {

        void subscribe(BufferedImage image);
        void finished();
        void error(String message, Throwable exception);
    }
}
