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

            futurePriceData.add(new EnergyDataPoint(
                    timestamp,
                    findThreeDayAverageUsage(timestamp, source),
                    vCost.getPricePerKWH(),
                    source
            ));
        }

        return futurePriceData;
    }

    public double findThreeDayAverageUsage(DateTime timestamp, String source){

        EnergyDataPoint freshestEDP = null;
       int count = 0;
        while (true){
            count++;

            DateTime timestampMinusOneWeek = timestamp.minusWeeks(count);
            Optional<EnergyDataPoint> current = Optional.of(repository.findByEndTimeStampAndSource(timestampMinusOneWeek, source));

            if (current.isPresent()){

                freshestEDP = current.get();

                break;
            }
        }

        double first = freshestEDP.getConsumptionInKWH();
        double second = repository.findByEndTimeStampAndSource(freshestEDP.getEndTimeStamp().minusWeeks(1), source).getConsumptionInKWH();
        double third = repository.findByEndTimeStampAndSource(freshestEDP.getEndTimeStamp().minusWeeks(2), source).getConsumptionInKWH();

        return (first + second + third) / 3;
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
