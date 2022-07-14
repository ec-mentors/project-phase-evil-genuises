package io.evilgeniuses.energy_optimization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConsumptionPriceCalculatorTest {
    @Autowired
    ConsumptionPriceCalculator consumptionPriceCalculator;

    @Test
    void getMonthlyPrice() {
        var result = consumptionPriceCalculator.getMonthlyPrice(1);
        assertEquals(0.37, result, 0.0000001);
    }

    @Test
    void getPriceForEveryMonthDaDbCategory() {
        var result = consumptionPriceCalculator.getPriceForEveryMonthDaDbCategory();
        System.out.println(result);
        assertEquals(12, result.size());

    }

    @Test
    void getPriceForEveryMonthDdDeCategory() {
        var result = consumptionPriceCalculator.getPriceForEveryMonthDdDeCategory();
        System.out.println(result);
        assertEquals(12, result.size());}
}