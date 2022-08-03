package io.evilgeniuses.energy_optimization.parsing;

import io.evilgeniuses.energy_optimization.parsing.FileParser;
import io.evilgeniuses.energy_optimization.parsing.JsonParser;
import io.evilgeniuses.energy_optimization.repositories.EnergyDataPointRepository;
import io.evilgeniuses.energy_optimization.repositories.VariableCostRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    ApplicationRunner CsvRunner(FileParser parser, EnergyDataPointRepository repository) {
        return args -> {
            if (repository.findAll().isEmpty()) {
                parser.parseAndSave();
            }
        };


    }
}
