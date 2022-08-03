package io.evilgeniuses.energy_optimization;

import io.evilgeniuses.energy_optimization.services.ConsumptionCalculator;
import io.evilgeniuses.energy_optimization.services.ConsumptionPriceCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
class ConsumptionPriceCalculatorTest {
    @Autowired
    ConsumptionPriceCalculator consumptionPriceCalculator;

    @MockBean
    ConsumptionCalculator consumptionCalculator;

    @Test
    void getMonthlyPerFixedPrice() {
        consumptionPriceCalculator.getMonthlyPerFixedPrice(1, "csv1");
        Mockito.when(consumptionCalculator.getConsumptionPerMonth(1, "csv1")).thenReturn(0.0);
        Mockito.verify(consumptionCalculator).getConsumptionPerMonth(1, "csv1");
    }
}