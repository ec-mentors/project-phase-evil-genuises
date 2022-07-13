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
        var result = consumptionCalculator.getConsumptionPerMonthCSVOne(1);
        assertEquals(106.6145, result, 0.0000001);
    }

    @Test
    void getConsumptionPerMonthCSVOneFebruary() {
        var result = consumptionCalculator.getConsumptionPerMonthCSVOne(2);
        assertEquals(91.8473799, result, 0.0000001);
    }

    @Test
    void getConsumptionPerMonthCSVTwoJanuary() {
        var result = consumptionCalculator.getConsumptionPerMonthCSVTwo(1);
        assertEquals(106.074519999, result, 0.0000001);
    }


    @Test
    void getConsumptionPerMonthCSVTwoFebruary() {
        var result = consumptionCalculator.getConsumptionPerMonthCSVTwo(2);
        assertEquals(92.72990000, result, 0.0000001);
    }

    @Test
    void getConsumptionPerMonthCSVTwoDecember() {
        var result = consumptionCalculator.getConsumptionPerMonthCSVTwo(12);
        assertEquals(102.731169999, result, 0.0000001);
    }

}