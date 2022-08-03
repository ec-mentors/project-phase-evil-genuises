package io.evilgeniuses.energy_optimization;

import com.opencsv.bean.CsvToBeanBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileParser {

    private final EnergyDataPointRepository repository;

    private final VariableCostRepository costRepository;




    private final VariablePriceFinder priceFinder;
    private final String pathDaDb;
    private final String pathDdDe;
    private final String sourceOne;
    private final String sourceTwo;

    public FileParser(EnergyDataPointRepository repository, VariableCostRepository costRepository, VariablePriceFinder priceFinder,
                      @Value("${loadprofile.pathone}")String pathDaDb,
                      @Value("${loadprofile.pathtwo}")String pathDdDe,
                      @Value("${loadprofile.sourceone}")String sourceOne,
                      @Value("${loadprofile.sourcetwo}")String sourceTwo) {
        this.repository = repository;
        this.costRepository = costRepository;
        this.priceFinder = priceFinder;
        this.pathDaDb = pathDaDb;
        this.pathDdDe = pathDdDe;
        this.sourceOne = sourceOne;
        this.sourceTwo = sourceTwo;
    }

        void parseAndSave() {

            List<EnergyDataPoint> output = new ArrayList<>();


            FileReader fileReaderFile1 = null;
            try {
                fileReaderFile1 = new FileReader(pathDaDb);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);

            }
            CsvToBeanBuilder<LoadProfilePoint> builder = new CsvToBeanBuilder<>(fileReaderFile1);

            var dataCsvFile1 = builder.withType(LoadProfilePoint.class)
                    .withSeparator(';')
                    .build()
                    .parse();



            FileReader fileReaderFile2 = null;
            try {
                fileReaderFile2 = new FileReader(pathDdDe);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);

            }

            CsvToBeanBuilder<LoadProfilePoint> builder2 = new CsvToBeanBuilder<>(fileReaderFile2);

            var dataCsvFile2 = builder2.withType(LoadProfilePoint.class)
                    .withSeparator(';')
                    .build()
                    .parse();

            //repository.saveAll(createEDPs(dataCsvFile1, "CSV1"));
            //repository.saveAll(createEDPs(dataCsvFile2, "CSV2"));

            output.addAll(createEDPs(dataCsvFile1, "CSV1"));
            output.addAll(createEDPs(dataCsvFile2, "CSV2"));
            repository.saveAll(output);




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
