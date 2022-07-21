package io.evilgeniuses.energy_optimization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MonthDataPoint {

    private String monthName;
    private double usage;

    private double fixedCost;

    private double variableCost;

    private double difference;

    public String getMonthName() {
        return monthName;
    }

    public double getUsage() {
        return usage;
    }

    public double getFixedCost() {
        return fixedCost;
    }

    public double getVariableCost() {
        return variableCost;
    }

    public double getDifference() {
        return difference;
    }
}
