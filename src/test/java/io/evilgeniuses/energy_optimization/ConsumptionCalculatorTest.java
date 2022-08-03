package io.evilgeniuses.energy_optimization;

import io.evilgeniuses.energy_optimization.repositories.EnergyDataPointRepository;
import io.evilgeniuses.energy_optimization.services.ConsumptionCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

@SpringBootTest
class ConsumptionCalculatorTest {
    @Autowired
    ConsumptionCalculator consumptionCalculator;

    @MockBean
    EnergyDataPointRepository repository;

    @Test
    void getConsumptionPerMonth() {
        String source = "csv1";
        consumptionCalculator.getConsumptionPerMonth(1, source);
        Mockito.when(repository.findBySource(source)).thenReturn(new ArrayList<>());
        Mockito.verify(repository).findBySource(source);
    }
}