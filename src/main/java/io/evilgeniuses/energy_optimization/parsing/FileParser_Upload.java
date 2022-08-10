package io.evilgeniuses.energy_optimization.parsing;

import com.opencsv.bean.CsvToBeanBuilder;
import io.evilgeniuses.energy_optimization.dataclasses.EnergyDataPoint;
import io.evilgeniuses.energy_optimization.dataclasses.LoadProfilePoint;
import io.evilgeniuses.energy_optimization.dataclasses.VariableCost;
import io.evilgeniuses.energy_optimization.frontend.services.FrontendDataManager;
import io.evilgeniuses.energy_optimization.repositories.EnergyDataPointRepository;
import io.evilgeniuses.energy_optimization.repositories.VariableCostRepository;
import io.evilgeniuses.energy_optimization.services.VariablePriceFinder;
import io.evilgeniuses.energy_optimization.storage.StorageService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileParser_Upload {

    private final EnergyDataPointRepository repository;
    private final VariableCostRepository costRepository;
    private final VariablePriceFinder priceFinder;
    private final StorageService storageService;
    private final FrontendDataManager frontendDataManager;

    public FileParser_Upload(EnergyDataPointRepository repository,
                             VariableCostRepository costRepository,
                             VariablePriceFinder priceFinder,
                             StorageService storageService, FrontendDataManager frontendDataManager) {
        this.repository = repository;
        this.costRepository = costRepository;
        this.priceFinder = priceFinder;
        this.storageService = storageService;
        this.frontendDataManager = frontendDataManager;
    }

    public void parseAndSave(String path) {

        List<EnergyDataPoint> output = new ArrayList<>();


        FileReader fileReaderFile1 = null;
        try {
            fileReaderFile1 = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
        CsvToBeanBuilder<LoadProfilePoint> builder = new CsvToBeanBuilder<>(fileReaderFile1);

        var dataCsvFile1 = builder.withType(LoadProfilePoint.class)
                .withSeparator(';')
                .build()
                .parse();

        var filenames = storageService.loadAll()
                .map(onePath -> onePath.getFileName().toString())
                .toList();
        output.addAll(createEDPs(dataCsvFile1, filenames.get(filenames.size() -1)));
        frontendDataManager.addToSourceKeys(filenames.get(filenames.size() -1));
        repository.saveAll(output);


//        possible Way to parse the Dateformat from Smart Meter File

//          String pattern = "dd.MM.yyyy HH:mm";
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//                    Date date = simpleDateFormat.parse("1.3.2020 0:00");
//                    System.out.println(new DateTime(date));
//                    System.out.println(date);


    }

    private List<EnergyDataPoint> createEDPs(List<LoadProfilePoint> input, String source) {
        List<DateTime> timestamps = new ArrayList<>();
        List<EnergyDataPoint> output = new ArrayList<>();
        List<LoadProfilePoint> inputs = new ArrayList<>(input);
        List<VariableCost> variableCosts = costRepository.findAll();


        for (int i = 1; i <= input.size(); i++) {
            if (i % 4 == 0) {
                timestamps.add(new DateTime(input.get(i - 1).getEndTimeStamp()));
            }
        }

        for (DateTime timestamp : timestamps) {
            double combinedUsage = 0;
            for (int j = 0; j < 4; j++) {
                combinedUsage += inputs.get(0).getConsumption();
                inputs.remove(0);
            }
            output.add(
                    new EnergyDataPoint(
                            timestamp,
                            combinedUsage,
                            getPriceForTimestamp(timestamp.getMillis(), variableCosts),
                            source));
            combinedUsage = 0;
        }
        return output;
    }

    private double getPriceForTimestamp(double timestamp, List<VariableCost> variableCosts) {
        for (VariableCost cost : variableCosts) {
            if (timestamp == cost.getEndTimeStamp()) {
                return cost.getPricePerKWH();
            }
        }
        return 0;

    }
}
