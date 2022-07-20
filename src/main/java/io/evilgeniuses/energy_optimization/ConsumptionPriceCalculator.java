package io.evilgeniuses.energy_optimization;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumptionPriceCalculator {

    private final ConsumptionCalculator consumptionCalculator;
    private final double fixedPrice = 0.27600;
    private final EnergyDataPointRepository repository;

    public double getFixedPrice() {
        return fixedPrice;
    }

    public ConsumptionPriceCalculator(ConsumptionCalculator consumptionCalculator, EnergyDataPointRepository repository) {
        this.consumptionCalculator = consumptionCalculator;
        this.repository = repository;
    }

    public double getMonthlyPerFixedPrice(int month, String source) {
        var consumptionPerMonth = consumptionCalculator.getConsumptionPerMonth(month, source);
        return Math.round((consumptionPerMonth * fixedPrice) * 100) / 100.0;
    }

    public List<Double> getPriceForEveryMonth(String source) {
        List<Double> monthlyCosts = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthlyCosts.add(getMonthlyPerFixedPrice(i, source));
        }
        return monthlyCosts;
    }

    public List<MonthDataPoint> getAllMonthDataPointsForAYear(String source) {
        List<String> monthNames = List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        List<MonthDataPoint> output = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            output.add(new MonthDataPoint(monthNames.get(i - 1),
                    consumptionCalculator.getConsumptionPerMonth(i, source),
                    getMonthlyPerFixedPrice(i, source)));
        }

        return output;
    }
}
