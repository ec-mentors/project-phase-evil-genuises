package io.evilgeniuses.energy_optimization;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;

import java.util.Date;

public class LoadProfilePoint {

    private String id;

    @CsvBindByName(column = "Ende Ablesezeitraum",required = true)
    @CsvDate("yyyy-MM-dd'T'HH:mm")
    private Date endTimeStamp;

    @CsvBindByName(column = "Messintervall")
    private String measuringInterval;

    @CsvBindByName(column = "Abrechnungsmaßeinheit")
    private String measurementUnit;

    @CsvBindByName(column = "Verbrauch", locale = "de-DE")
    @CsvNumber("#.###")
    private double consumption;
}
