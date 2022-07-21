package io.evilgeniuses.energy_optimization;

import com.opencsv.bean.CsvToBeanBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class CsvFileParser {

    List<LoadProfilePointWithDateTime> parse(String path) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
        CsvToBeanBuilder<LoadProfilePoint> builder = new CsvToBeanBuilder<>(fileReader);

        var data = builder.withType(LoadProfilePoint.class)
                .withSeparator(';')
                .build()
                .parse();

        return data.stream().map(ele -> new LoadProfilePointWithDateTime(new DateTime(ele.getEndTimeStamp()),
                        ele.getMeasuringInterval(),
                        ele.getMeasurementUnit(), ele.getConsumption()))
                .toList();
    }
}
