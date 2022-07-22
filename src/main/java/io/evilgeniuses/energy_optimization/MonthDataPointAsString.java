package io.evilgeniuses.energy_optimization;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonthDataPointAsString {

    private String monthName;
    private String usage;

    private String fixedCost;

    private String variableCost;

    private String difference;

    public String getMonthName() {
        return monthName;
    }

    public String getUsage() {
        return usage;
    }

    public String getFixedCost() {
        return fixedCost;
    }

    public String getVariableCost() {
        return variableCost;
    }

    public String getDifference() {
        return difference;
    }
}
