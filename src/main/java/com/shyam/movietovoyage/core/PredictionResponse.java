package com.shyam.movietovoyage.core;

import java.util.List;

public class PredictionResponse {

    private List<PredictionResult> predictionResults;
    private String videoUuid;
    private long timestamp;
    private String base64Image;

    public PredictionResponse(List<PredictionResult> predictionResults, String videoUuid, long timestamp) {
        this.predictionResults = predictionResults;
        this.videoUuid = videoUuid;
        this.timestamp = timestamp;
    }

    public List<PredictionResult> getPredictionResults() {
        return predictionResults;
    }

    public String getVideoUuid() {
        return videoUuid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public void setPredictionResults(List<PredictionResult> predictionResults) {
        this.predictionResults = predictionResults;
    }

    public void setVideoUuid(String videoUuid) {
        this.videoUuid = videoUuid;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PredictionResponse{" +
                "predictionResults=" + predictionResults +
                ", videoUuid='" + videoUuid + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
