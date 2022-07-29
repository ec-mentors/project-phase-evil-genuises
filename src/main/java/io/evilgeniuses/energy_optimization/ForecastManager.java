package io.evilgeniuses.energy_optimization;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            EnergyDataPoint current = repository.findByEndTimeStampAndSource(newTimestamp, source);
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
