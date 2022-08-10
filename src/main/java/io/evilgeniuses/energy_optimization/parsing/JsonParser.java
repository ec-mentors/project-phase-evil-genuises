package io.evilgeniuses.energy_optimization.parsing;

import com.google.gson.Gson;
import io.evilgeniuses.energy_optimization.dataclasses.DataEntry;
import io.evilgeniuses.energy_optimization.dataclasses.VariableCost;
import io.evilgeniuses.energy_optimization.parsing.AwattarClient;
import io.evilgeniuses.energy_optimization.repositories.VariableCostRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class JsonParser {

    private final VariableCostRepository repository;
    private final AwattarClient client;

    public JsonParser(VariableCostRepository repository, AwattarClient client) {
        this.repository = repository;
        this.client = client;
    }

    //unused because we get Data directly from the API

//    public void parseAndSave() {
//        Gson gson = new Gson();
//        try {
//            var list = Arrays.stream(gson.fromJson(new FileReader("src/main/resources/2019.json"), DataEntry[].class)).toList();
//            List<VariableCost> energyList = new ArrayList<>();
//
//            for (DataEntry entry : list) {
//                energyList.add(new VariableCost(entry.getEnd_timestamp(),
//                        (Math.round(entry.getMarketprice() / 1000 * 10000) / 10000.0)));
//            }
//
//            repository.saveAll(energyList);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
    public void saveFromApi(){
        var data = client.getDataFromYear2019();
        var dataEntries = data.getData();
        List<VariableCost> energyList = new ArrayList<>();

        for (DataEntry entry : dataEntries) {
            energyList.add(new VariableCost(entry.getStart_timestamp(),
                    (Math.round(entry.getMarketprice() / 1000 * 10000) / 10000.0)));

        }
        repository.saveAll(energyList);
    }
}
