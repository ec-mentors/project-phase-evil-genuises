package io.evilgeniuses.energy_optimization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CsvFileParser2Test {
@Autowired
CsvFileParser2 csvFileParser2;
@Autowired
EnergyDataPointRepository energyDataPointRepository;

private final String sourceOne = "CSV1Test";
private final String sourceTwo = "CSV2Test";

    @BeforeEach
    void beforeAll() {
        energyDataPointRepository.deleteAll();

    }

    @Test
    void parseAndSave() {
        csvFileParser2.parseAndSave();
        Assertions.assertEquals(3, energyDataPointRepository.findBySource(sourceOne).size());
        Assertions.assertEquals(2, energyDataPointRepository.findBySource(sourceTwo).size());

    }


}