package io.evilgeniuses.energy_optimization;

import lombok.Getter;

public class Item {

    @Getter
    private String id;
    @Getter
    private String text;


    public Item(String text) {
        this.text = text;
    }
}
