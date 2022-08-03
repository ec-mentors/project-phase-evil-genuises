package io.evilgeniuses.energy_optimization.services;


import io.evilgeniuses.energy_optimization.dataclasses.EnergyDataPoint;
import io.evilgeniuses.energy_optimization.repositories.EnergyDataPointRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionCalculator {

    private final EnergyDataPointRepository repository;

    public ConsumptionCalculator(EnergyDataPointRepository repository) {
        this.repository = repository;
    }
    public double getConsumptionPerMonth(int numberOfMonth, String source) {
        var data = repository.findBySource(source);
        var sumOfMonth = data.stream()
                .filter(v -> v.getEndTimeStamp().monthOfYear().get() == numberOfMonth)
                .map(EnergyDataPoint::getConsumptionInKWH)
                .reduce(0.0, Double::sum);
        return Math.round(sumOfMonth * 100) / 100.0;
    }
}

