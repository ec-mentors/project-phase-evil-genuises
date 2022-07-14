package io.evilgeniuses.energy_optimization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionCalculator {

    private final CsvFileParser csvFileParser;
    private final String pathOne;
    private final String pathTwo;

    public ConsumptionCalculator(CsvFileParser csvFileParser,
                                 @Value("${loadprofile.pathone}") String pathOne,
                                 @Value("${loadprofile.pathtwo}") String pathTwo) {
        this.csvFileParser = csvFileParser;
        this.pathOne = pathOne;
        this.pathTwo = pathTwo;
    }

    public double getConsumptionPerMonthCSVOne(int numberOfMonth) {
        var data = csvFileParser.parse(pathOne);

        var sumOfMonth = data.stream()
                .filter(v -> v.getEndTimeStamp().monthOfYear().get() == numberOfMonth)
                .map(LoadProfilePointWithDateTime::getConsumption)
                .reduce(0.0, Double::sum);
        return Math.round(sumOfMonth * 100) / 100.0;


    }

    public double getConsumptionPerMonthCSVTwo(int numberOfMonth) {
        var data = csvFileParser.parse(pathTwo);

        var sumOfMonth = data.stream()
                .filter(v -> v.getEndTimeStamp().monthOfYear().get() == numberOfMonth)
                .map(LoadProfilePointWithDateTime::getConsumption)
                .reduce(0.0, Double::sum);
        return Math.round(sumOfMonth * 100) / 100.0;
    }
}

