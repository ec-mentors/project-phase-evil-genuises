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

    public double getMonthlyPrice(int month) {
        var consumptionPerMonth = consumptionCalculator.getConsumptionPerMonthCSVOne(month);
        return Math.round((consumptionPerMonth * fixedPrice) * 100) / 100.0;
    }

    public List<Double> getPriceForEveryMonthDaDbCategory() {
        List<Double> monthlyCosts = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {

            monthlyCosts.add(getMonthlyPrice(i));
        }
        return monthlyCosts;
    }

    public List<Double> getPriceForEveryMonthDdDeCategory() {
        List<Double> monthlyCosts = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {

            monthlyCosts.add(getMonthlyPrice(i));
        }
        return monthlyCosts;
    }

}
