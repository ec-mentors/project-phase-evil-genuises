package io.evilgeniuses.energy_optimization;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FrontendDataManager {

    EnergyDataPointRepository dataPointRepository;

    public FrontendDataManager(EnergyDataPointRepository dataPointRepository) {
        this.dataPointRepository = dataPointRepository;
    }

    public List<MonthDataPoint> getMonths(String source) {
        List<MonthDataPoint> output = new ArrayList<>();
        List<String> months = List.of(
                "Januar",
                "Februar",
                "März",
                "April",
                "Mai",
                "Juni",
                "Juli",
                "August",
                "September",
                "Oktober",
                "November",
                "Dezember");

        for (int i = 0; i < 12; i++) {
            final int currentLoopInt = i;
            List<EnergyDataPoint> dataPoints = dataPointRepository.findBySource(source).stream()
                            .filter(element -> element.getEndTimeStamp().toString("MMMM").equals(months.get(currentLoopInt)))
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
            output.add(new MonthDataPoint(months.get(i), usage, fixedCost, variableCost, difference));

         }

        return output;

    }

}
