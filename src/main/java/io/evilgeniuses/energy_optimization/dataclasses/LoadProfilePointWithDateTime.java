package io.evilgeniuses.energy_optimization.dataclasses;

import org.joda.time.DateTime;

public class LoadProfilePointWithDateTime {

    private DateTime endTimeStamp;

    private String measuringInterval;

    private String measurementUnit;

    private double consumption;


    public LoadProfilePointWithDateTime(DateTime endTimeStamp, String measuringInterval, String measurementUnit, double consumption) {
        this.endTimeStamp = endTimeStamp;
        this.measuringInterval = measuringInterval;
        this.measurementUnit = measurementUnit;
        this.consumption = consumption;
    }

    public DateTime getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(DateTime endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getMeasuringInterval() {
        return measuringInterval;
    }

    public void setMeasuringInterval(String measuringInterval) {
        this.measuringInterval = measuringInterval;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }
}
