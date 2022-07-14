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


    // Actually not a good solution - the path argument decide which path will be used ( 1 = Da+Db 2 = Dd+De)
    public double getConsumptionPerMonth(int numberOfMonth, int path) {
        if (path == 1) {
            var data = csvFileParser.parse(pathOne);
            var sumOfMonth = data.stream()
                    .filter(v -> v.getEndTimeStamp().monthOfYear().get() == numberOfMonth)
                    .map(LoadProfilePointWithDateTime::getConsumption)
                    .reduce(0.0, Double::sum);
            return Math.round(sumOfMonth * 100) / 100.0;
        }
        if (path == 2) {
            var data = csvFileParser.parse(pathTwo);

            var sumOfMonth = data.stream()
                    .filter(v -> v.getEndTimeStamp().monthOfYear().get() == numberOfMonth)
                    .map(LoadProfilePointWithDateTime::getConsumption)
                    .reduce(0.0, Double::sum);
            return Math.round(sumOfMonth * 100) / 100.0;
        }
        return 0.0;
    }
}

