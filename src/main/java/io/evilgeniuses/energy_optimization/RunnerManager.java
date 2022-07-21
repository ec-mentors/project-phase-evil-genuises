package io.evilgeniuses.energy_optimization;

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
    ApplicationRunner CsvRunner(CsvFileParser2 parser, EnergyDataPointRepository repository) {
        return args -> {
            if (repository.findAll().isEmpty()) {
                parser.parseAndSave();
            }
        };
    }
}
