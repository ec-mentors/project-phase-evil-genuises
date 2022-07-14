package io.evilgeniuses.energy_optimization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileParserTest {

    CsvFileParser csvFileParser = new CsvFileParser();


    @Test
    void parse() {
        var list = csvFileParser.parse("src/test/resources/test.csv");
        assertEquals(12, list.size());
    }
    @Test
    void wrongPathWasGiven(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            csvFileParser.parse("wrong Path");
        });
    }

}