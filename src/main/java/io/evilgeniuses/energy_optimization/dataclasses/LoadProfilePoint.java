package io.evilgeniuses.energy_optimization.dataclasses;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;

public class LoadProfilePoint {

    private String id;

    @CsvBindByName(column = "Ende Ablesezeitraum",required = true)
//    @CsvDate("yyyy-MM-dd'T'HH:mm")
    private String endTimeStamp;

    @CsvBindByName(column = "Messintervall")
    private String measuringInterval;

    @CsvBindByName(column = "Abrechnungsmaßeinheit")
    private String measurementUnit;

    @CsvBindByName(column = "Verbrauch", locale = "de-DE")
    @CsvNumber("#.###")
    private double consumption;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
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
