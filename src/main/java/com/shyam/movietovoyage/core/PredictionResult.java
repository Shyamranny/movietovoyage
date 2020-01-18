package com.shyam.movietovoyage.core;

public class PredictionResult {

    private String rank;
    private float probability;
    private double lat;
    private double lng;

    private PredictionResult(String rank, float probability, double lat, double lng) {
        this.rank = rank;
        this.probability = probability;
        this.lat = lat;
        this.lng = lng;
    }

    public static PredictionResult createFromResult(String result) {
        String[] results = result.split(" ");

        return new PredictionResult(
                results[0].replace(",","").split("=")[1],
                Float.parseFloat(results[1].replace(",","").split("=")[1]),
                Double.parseDouble(results[2].replace(",","").split("=")[1]),
                Double.parseDouble(results[3].replace(",","").split("=")[1])
        );
    }
}
