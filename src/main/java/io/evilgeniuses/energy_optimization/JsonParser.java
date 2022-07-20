package io.evilgeniuses.energy_optimization;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class JsonParser {

    private final VariableCostRepository repository;

    public JsonParser(VariableCostRepository repository) {
        this.repository = repository;
    }

    public void parseAndSave() {
        Gson gson = new Gson();
        try {
            var list = Arrays.stream(gson.fromJson(new FileReader("src/main/resources/2019.json"), DataEntry[].class)).toList();
            List<VariableCost> energyList = new ArrayList<>();

            for (DataEntry entry : list) {
                energyList.add(new VariableCost(entry.getEnd_timestamp(),
                        (Math.round(entry.getMarketprice() / 1000 * 10000) / 10000.0)));
            }

            repository.saveAll(energyList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
