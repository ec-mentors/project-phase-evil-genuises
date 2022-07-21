package io.evilgeniuses.energy_optimization;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VariablePriceFinder {

    VariableCostRepository repo;

    public VariablePriceFinder(VariableCostRepository repo) {
        this.repo = repo;
    }

    public double getPriceForTimestamp(long unixTimeInput) {
        Optional<VariableCost> cost = repo.findByEndTimeStamp(unixTimeInput);
        return cost.map(VariableCost::getPricePerKWH).orElse(0.0);
    }
}
