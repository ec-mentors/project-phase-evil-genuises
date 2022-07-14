package io.evilgeniuses.energy_optimization;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Endpoint {
    private AwattarClient client;

    public Endpoint(AwattarClient client) {
        this.client = client;
    }

    @GetMapping("/future")
    MarketData get(){
        return client.get();
    }
}
