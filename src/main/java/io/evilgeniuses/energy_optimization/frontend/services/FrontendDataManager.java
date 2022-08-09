package io.evilgeniuses.energy_optimization.frontend.services;

import io.evilgeniuses.energy_optimization.dataclasses.EnergyDataPoint;
import io.evilgeniuses.energy_optimization.repositories.EnergyDataPointRepository;
import io.evilgeniuses.energy_optimization.services.ForecastManager;
import io.evilgeniuses.energy_optimization.frontend.dataclasses.DiagramData;
import io.evilgeniuses.energy_optimization.frontend.dataclasses.ForecastDataPoint;
import io.evilgeniuses.energy_optimization.frontend.dataclasses.MonthDataPoint;
import io.evilgeniuses.energy_optimization.frontend.dataclasses.MonthDataPointAsString;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FrontendDataManager {

    private final EnergyDataPointRepository dataPointRepository;

    private final ForecastManager forecastManager;

    private final List<String> sourceKeys = new ArrayList<>();

    public FrontendDataManager(EnergyDataPointRepository dataPointRepository, ForecastManager forecastManager) {
        this.dataPointRepository = dataPointRepository;
        this.forecastManager = forecastManager;
    }

    public List<MonthDataPointAsString> getMonths(String source) {
        List<MonthDataPointAsString> outputStrings = new ArrayList<>();
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




        //border-collapse: collapse;

        for (MonthDataPoint point : output) {
            outputStrings.add(new MonthDataPointAsString(
                    point.getMonthName(),
                    String.valueOf(point.getUsage()) + " kWh",
                    String.valueOf(point.getFixedCost()) + " €",
                    String.valueOf(point.getVariableCost()) + " €",
                    String.valueOf(point.getDifference()) + " €"
            ));


        }
        return outputStrings;

    }

    public List<ForecastDataPoint> getForecast(String source) {
        var futureData = forecastManager.getFutureData(source);
        List<ForecastDataPoint> output = new ArrayList<>();
        double totalUsage = 0;
        double totalBillingAmount = 0;
        for (EnergyDataPoint point : futureData) {
            double currentConsumption = point.getConsumptionInKWH();
            double currentPrice = point.getPricePerKWH();
            double currentBillingAmount = currentConsumption * currentPrice;
            totalUsage += currentConsumption;
            totalBillingAmount += currentBillingAmount;

            output.add(
                    new ForecastDataPoint(
                            String.valueOf(point.getEndTimeStamp()),
                            String.valueOf(currentConsumption).substring(0, 5) + " kWh",
                            String.valueOf(currentPrice) + " €",
                            String.valueOf(currentPrice * currentConsumption).substring(0, 4)
                            + " €"));

        }
        //we need amount of hours

        String fullDayOutputString = "";
        if (output.size() == 24) {
            fullDayOutputString = "FULL DAY COMBINED";
        } else {
            fullDayOutputString = "NEXT " + output.size() + " HOURS COMBINED";
        }

        output.add(new ForecastDataPoint(fullDayOutputString,
                String.valueOf(totalUsage).substring(0,5) + " kWh",
                "",
                String.valueOf(totalBillingAmount).substring(0, 4) + " €"));


        return output;
    }

    public List<DiagramData> getDiagramData(String source){
        var diagramData = forecastManager.getFutureData(source);
        List<DiagramData> output = new ArrayList<>();

        for (EnergyDataPoint point : diagramData) {

            output.add(
                    new DiagramData(
                            point.getEndTimeStamp().getHourOfDay(),
                            point.getConsumptionInKWH(),
                            point.getPricePerKWH()
                            ));

        }
        return output;
    }

    public List<String> getSourceKeys() {
        return sourceKeys;
    }

    public void addToSourceKeys(String key) {
        sourceKeys.add(key);
    }

}
