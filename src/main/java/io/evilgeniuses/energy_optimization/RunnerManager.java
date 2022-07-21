package io.evilgeniuses.energy_optimization;

import org.springframework.boot.ApplicationRunner;

public class RunnerManager {

    private final CsvFileParser2 parser;
    private final EnergyDataPointRepository repository;

    public RunnerManager(CsvFileParser2 parser, EnergyDataPointRepository repository) {
        this.parser = parser;
        this.repository = repository;
    }

    ApplicationRunner jsonRunner() {
        return args -> {
            if (repository.findAll().isEmpty()) {
                parser.parseAndSave();
            }
        };
    }

    ApplicationRunner csvRunner() {
        return args -> {
            if (repository.findAll().isEmpty()) {
                parser.parseAndSave();
            }
        };
    }
}
