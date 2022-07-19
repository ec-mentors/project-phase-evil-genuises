package io.evilgeniuses.energy_optimization;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonParser {

    public List<DataEntry> parse(String path) {
        Gson gson = new Gson();
        try {
            return Arrays.stream(gson.fromJson(new FileReader(path), DataEntry[].class)).toList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
