package io.evilgeniuses.energy_optimization.services;

import io.evilgeniuses.energy_optimization.dataclasses.DataEntry;
import io.evilgeniuses.energy_optimization.dataclasses.EnergyDataPoint;
import io.evilgeniuses.energy_optimization.dataclasses.VariableCost;
import io.evilgeniuses.energy_optimization.parsing.AwattarClient;
import io.evilgeniuses.energy_optimization.repositories.EnergyDataPointRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ForecastManager {
    private final EnergyDataPointRepository repository;
    private final AwattarClient client;

    public ForecastManager(EnergyDataPointRepository repository, AwattarClient client) {
        this.repository = repository;
        this.client = client;
    }

    public List<EnergyDataPoint> getFutureData(String source) {
        var futurePrices = getFutureCosts();
        List<EnergyDataPoint> futurePriceData = new ArrayList<>();


        for (VariableCost vCost : futurePrices) {
            var timestamp = new DateTime(vCost.getEndTimeStamp());
            DateTime newTimestamp = timestamp.withYear(2019);
            EnergyDataPoint current = new EnergyDataPoint(new DateTime(0), 0, 0, "---");
            EnergyDataPoint tryCurrent = repository.findByEndTimeStampAndSource(newTimestamp, source);
            if (tryCurrent != null) {
                current = tryCurrent;
            }
            futurePriceData.add(new EnergyDataPoint(
                    current.getEndTimeStamp().withYear(2022),
                    current.getConsumptionInKWH(),
                    vCost.getPricePerKWH(),
                    source
            ));
        }

        return futurePriceData;
    }

    private List<VariableCost> getFutureCosts() {
        var data = client.getDataFromFuture();
        var dataEntries = data.getData();
        List<VariableCost> energyList = new ArrayList<>();

        for (DataEntry entry : dataEntries) {
            energyList.add(new VariableCost(entry.getStart_timestamp(),
                    (Math.round(entry.getMarketprice() / 1000 * 10000) / 10000.0)));

        }
        return energyList;
    }
}