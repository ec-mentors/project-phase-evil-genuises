package io.evilgeniuses.energy_optimization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ConsumptionCalculatorTest {
@Autowired
ConsumptionCalculator consumptionCalculator;

    @Test
    void getConsumptionPerMonthCSVOneJanuary() {
        var result = consumptionCalculator.getConsumptionPerMonth(1,1);
        assertEquals(1.35, result, 0.0000001);
    }


    @Test
    void getConsumptionPerMonthCSVTwoDecember() {
        var result = consumptionCalculator.getConsumptionPerMonth(12,2);
        assertEquals(1.35, result, 0.0000001);
    }


}