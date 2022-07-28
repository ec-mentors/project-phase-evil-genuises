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

    public List<EnergyDataPoint> getFutureData(String source){
        var data = repository.findBySource(source);
        var futurePrices = getFutureCosts();
        List<EnergyDataPoint> futurePriceData = new ArrayList<>();


        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < futurePrices.size(); j++) {
                var hour = new DateTime(futurePrices.get(j).getEndTimeStamp()).hourOfDay().get();
                var dayOfMonth = new DateTime(futurePrices.get(j).getEndTimeStamp()).dayOfMonth().get();
                var month = new DateTime(futurePrices.get(j).getEndTimeStamp()).monthOfYear().get();
                if (data.get(i).getEndTimeStamp().getHourOfDay() == hour
                        && data.get(i).getEndTimeStamp().dayOfMonth().get() == dayOfMonth
                        && data.get(i).getEndTimeStamp().monthOfYear().get() == month){
                    var dataEntry = data.get(i);
                    dataEntry.setPricePerKWH(futurePrices.get(j).getPricePerKWH());
                    futurePriceData.add(dataEntry);
                }

            }

        }
        return futurePriceData;
    }

    public List<EnergyDataPoint> getFutureData2(String source) {
        var futurePrices = getFutureCosts();
        List<EnergyDataPoint> futurePriceData = new ArrayList<>();

        for (VariableCost vCost : futurePrices) {
            var timestamp = new DateTime(vCost.getEndTimeStamp());
            DateTime newTimestamp = timestamp.withYear(2019);
            EnergyDataPoint current = repository.findByEndTimeStamp(newTimestamp);
            futurePriceData.add(new EnergyDataPoint(
                    current.getEndTimeStamp(),
                    current.getConsumptionInKWH(),
                    vCost.getPricePerKWH(),
                    source
            ));
        }

        return futurePriceData;
    }




     private List<VariableCost> getFutureCosts(){
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
