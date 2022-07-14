package io.evilgeniuses.energy_optimization;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumptionPriceCalculator {

    private final ConsumptionCalculator consumptionCalculator;
    private final double fixedPrice = 0.27600;

    public double getFixedPrice() {
        return fixedPrice;
    }

    public ConsumptionPriceCalculator(ConsumptionCalculator consumptionCalculator) {
        this.consumptionCalculator = consumptionCalculator;
    }

    public double getMonthlyPrice(int month, int path) {
        var consumptionPerMonth = consumptionCalculator.getConsumptionPerMonth(month, path);
        return Math.round((consumptionPerMonth * fixedPrice) * 100) / 100.0;
    }

    public List<Double> getPriceForEveryMonth(int path) {
        List<Double> monthlyCosts = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthlyCosts.add(getMonthlyPrice(i, path));
        }
        return monthlyCosts;
    }

}
