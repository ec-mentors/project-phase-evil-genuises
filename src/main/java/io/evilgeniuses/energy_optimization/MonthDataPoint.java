package io.evilgeniuses.energy_optimization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MonthDataPoint {

    private String monthName;
    private double usage;
    private double cost;

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
