package io.evilgeniuses.energy_optimization.parsing;

import io.evilgeniuses.energy_optimization.repositories.EnergyDataPointRepository;
import io.evilgeniuses.energy_optimization.repositories.VariableCostRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RunnerManager {

    @Bean
    ApplicationRunner runner(JsonParser parser, VariableCostRepository repository) {
        return args -> {
            if (repository.findAll().isEmpty()) {
                parser.saveFromApi();
            }
        };
    }

    @Bean
    ApplicationRunner CsvRunner(EnergyDataPointRepository repository) {
        return args -> {
            //delete the DB with all Entities after each start
            if (!repository.findAll().isEmpty()) {
                repository.deleteAll();
            }
        };
    }
}

