package io.evilgeniuses.energy_optimization.deprecated;

import com.opencsv.bean.CsvToBeanBuilder;
import io.evilgeniuses.energy_optimization.dataclasses.EnergyDataPoint;
import io.evilgeniuses.energy_optimization.repositories.EnergyDataPointRepository;
import io.evilgeniuses.energy_optimization.dataclasses.LoadProfilePoint;
import io.evilgeniuses.energy_optimization.services.VariablePriceFinder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvFileParser2 {

    private final EnergyDataPointRepository repository;
    private final VariablePriceFinder priceFinder;
    private final String pathDaDb;
    private final String pathDdDe;
    private final String sourceOne;
    private final String sourceTwo;


    public CsvFileParser2(EnergyDataPointRepository repository, VariablePriceFinder priceFinder,
                          @Value("${loadprofile.pathone}")String pathDaDb,
                          @Value("${loadprofile.pathtwo}")String pathDdDe,
                          @Value("${loadprofile.sourceone}")String sourceOne,
                          @Value("${loadprofile.sourcetwo}")String sourceTwo) {
        this.repository = repository;
        this.priceFinder = priceFinder;
        this.pathDaDb = pathDaDb;
        this.pathDdDe = pathDdDe;
        this.sourceOne = sourceOne;
        this.sourceTwo = sourceTwo;
    }

    void parseAndSave() {
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

        var listFile1 = dataCsvFile1.stream().map(ele -> new EnergyDataPoint(new DateTime(ele.getEndTimeStamp()),
                        ele.getConsumption(), 0.27600, sourceOne))
                .toList();


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


        var listFile2 = dataCsvFile2.stream().map(ele -> new EnergyDataPoint(new DateTime(ele.getEndTimeStamp()),
                        ele.getConsumption(), 0.27600, sourceTwo))
                .toList();

        repository.saveAll(modifyTimestampFourToOne(listFile1, sourceOne));
        repository.saveAll(modifyTimestampFourToOne(listFile2, sourceTwo));


    }

    private List<EnergyDataPoint> modifyTimestampFourToOne(List<EnergyDataPoint> input, String source) {

        List<DateTime> timestamps = new ArrayList<>();
        List<EnergyDataPoint> output = new ArrayList<>();
        List<EnergyDataPoint> inputs = new ArrayList<>(input);

        for (int i = 1; i <= input.size(); i++) {
            if (i % 4 == 0) {
                timestamps.add(input.get(i - 1).getEndTimeStamp());
            }
        }

        for (DateTime timestamp : timestamps) {
            double combinedUsage = 0;
            for (int j = 0; j < 4; j++) {
                combinedUsage += inputs.get(0).getConsumptionInKWH();
                inputs.remove(0);
            }
            output.add(
                    new EnergyDataPoint(
                            timestamp,
                            combinedUsage,
                            priceFinder.getPriceForTimestamp(timestamp.getMillis()),
                            source));
            combinedUsage = 0;
        }
        return output;
    }
}
