package io.evilgeniuses.energy_optimization;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FrontendDataManager {

    EnergyDataPointRepository dataPointRepository;

    public FrontendDataManager(EnergyDataPointRepository dataPointRepository) {
        this.dataPointRepository = dataPointRepository;
    }

    public List<MonthDataPoint> getMonths(String source) {
        List<MonthDataPoint> output = new ArrayList<>();
        List<String> months = List.of(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December");

        for (int i = 0; i < 12; i++) {

            final int currentLoopInt = i;
            List<EnergyDataPoint> dataPoints = dataPointRepository.findBySource(source).stream()
                            .filter(element -> element.getEndTimeStamp().toString("MMMM", Locale.ENGLISH).equals(months.get(currentLoopInt)))
                                    .toList();

            double usage = 0;
            double fixedCost = 0;
            double variableCost = 0;
            for (EnergyDataPoint point : dataPoints) {
                double consumptionPerKWH = point.getConsumptionInKWH();
                usage += consumptionPerKWH;
                variableCost += point.getPricePerKWH() * consumptionPerKWH;
                fixedCost += 0.276 * consumptionPerKWH;
            }


            double difference = variableCost - fixedCost;
            output.add(new MonthDataPoint(months.get(i),
                    Math.round((usage) * 100) / 100.0,
                    Math.round((fixedCost) * 100) / 100.0,
                    Math.round((variableCost) * 100) / 100.0,
                    Math.round((difference) * 100) / 100.0));

         }

        double totalUsage = 0;
        double totalFixedCosts = 0;
        double totalVariableCosts = 0;
        double totalDifference = 0;

        for (MonthDataPoint point : output) {
            totalUsage += point.getUsage();
            totalFixedCosts += point.getFixedCost();
            totalVariableCosts += point.getVariableCost();
            totalDifference += point.getDifference();
        }

        //Math.round((number) * 100) / 100.0;

        output.add(new MonthDataPoint("Full Year",
                Math.round((totalUsage) * 100) / 100.0,
                Math.round((totalFixedCosts) * 100) / 100.0,
                Math.round((totalVariableCosts) * 100) / 100.0,
                Math.round((totalDifference) * 100) / 100.0));

        return output;


        //border-collapse: collapse;

        
    }

}
