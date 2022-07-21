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

    public double getMonthlyPerFixedPrice(int month, String source) {
        var consumptionPerMonth = consumptionCalculator.getConsumptionPerMonth(month, source);
        return Math.round((consumptionPerMonth * fixedPrice) * 100) / 100.0;
    }
}
