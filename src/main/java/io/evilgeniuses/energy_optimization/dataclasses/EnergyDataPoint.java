package io.evilgeniuses.energy_optimization.dataclasses;

import org.joda.time.DateTime;

public class EnergyDataPoint {

    private String id;
    private DateTime endTimeStamp;
    private double consumptionInKWH;
    private double pricePerKWH;
    private String source;

    public EnergyDataPoint(DateTime endTimeStamp, double consumptionInKWH, double pricePerKWH, String source) {
        this.endTimeStamp = endTimeStamp;
        this.consumptionInKWH = consumptionInKWH;
        this.pricePerKWH = pricePerKWH;
        this.source = source;
    }

    public EnergyDataPoint() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(DateTime endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public double getConsumptionInKWH() {
        return consumptionInKWH;
    }

    public void setConsumptionInKWH(double consumptionInKWH) {
        this.consumptionInKWH = consumptionInKWH;
    }

    public double getPricePerKWH() {
        return pricePerKWH;
    }

    public void setPricePerKWH(double pricePerKWH) {
        this.pricePerKWH = pricePerKWH;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "EnergyDataPoint{" +
                "id='" + id + '\'' +
                ", endTimeStamp=" + endTimeStamp +
                ", consumptionInKWH=" + consumptionInKWH +
                ", pricePerKWH=" + pricePerKWH +
                ", source='" + source + '\'' +
                '}';
    }
}
