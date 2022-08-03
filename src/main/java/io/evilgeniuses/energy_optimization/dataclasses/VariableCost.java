package io.evilgeniuses.energy_optimization.dataclasses;

public class VariableCost {
    private String id;
    private long endTimeStamp;
    private double pricePerKWH;

    public VariableCost(long endTimeStamp, double pricePerKWH) {
        this.endTimeStamp = endTimeStamp;
        this.pricePerKWH = pricePerKWH;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public double getPricePerKWH() {
        return pricePerKWH;
    }

    public void setPricePerKWH(double pricePerKWH) {
        this.pricePerKWH = pricePerKWH;
    }
}
