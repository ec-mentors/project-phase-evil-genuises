package io.evilgeniuses.energy_optimization.dataclasses;

public class DataEntry {
    private long start_timestamp;
    private long end_timestamp;
    private double marketprice;
    private String unit;

    public long getStart_timestamp() {
        return start_timestamp;
    }

    public long getEnd_timestamp() {
        return end_timestamp;
    }

    public double getMarketprice() {
        return marketprice;
    }

    public String getUnit() {
        return unit;
    }
}

