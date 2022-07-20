package io.evilgeniuses.energy_optimization;

import com.opencsv.bean.CsvToBeanBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class CsvFileParser2 {

    private final EnergyDataPointRepository repository;

    public CsvFileParser2(EnergyDataPointRepository repository) {
        this.repository = repository;
    }

    void parseAndSave() {
        FileReader fileReaderFile1 = null;
        try {
            fileReaderFile1 = new FileReader("src/main/resources/Da+Db_[1, 1].csv");
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
                        ele.getConsumption(), 0.27600, "CSV1"))
                .toList();


        FileReader fileReaderFile2 = null;
        try {
            fileReaderFile2 = new FileReader("src/main/resources/Dd+De_[1, 2].csv");
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
                        ele.getConsumption(), 0.27600, "CSV2"))
                .toList();

        repository.saveAll(listFile1);
        repository.saveAll(listFile2);


    }
}
