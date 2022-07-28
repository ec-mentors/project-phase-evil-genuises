package io.evilgeniuses.energy_optimization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ForecastManagerTest {

    @Autowired
    ForecastManager manager;

    @Test
    void getFutureData2() {
        var output = manager.getFutureData2("CSV2");
        System.out.println("Endtimestamp: " + output.get(0).getEndTimeStamp());
        System.out.println("Price per kWh: " + output.get(0).getPricePerKWH());
        System.out.println("Consumption: " + output.get(0).getConsumptionInKWH());


    }
}