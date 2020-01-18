package com.shyam.movietovoyage.core;

import java.util.List;

public class PredictionResponse {

    private List<PredictionResult> predictionResults;
    private String videoUuid;
    private long timestamp;
    private String fileName;

    public PredictionResponse(List<PredictionResult> predictionResults, String videoUuid, long timestamp, String fileName) {
        this.predictionResults = predictionResults;
        this.videoUuid = videoUuid;
        this.timestamp = timestamp;
        this.fileName = fileName;
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

    public void setPredictionResults(List<PredictionResult> predictionResults) {
        this.predictionResults = predictionResults;
    }

    public void setVideoUuid(String videoUuid) {
        this.videoUuid = videoUuid;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
