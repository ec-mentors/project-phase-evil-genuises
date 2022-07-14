package io.evilgeniuses.energy_optimization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class AwattarClient {

    private final RestTemplate restTemplate;
    private final String url;

    public AwattarClient(RestTemplate restTemplate, @Value("${awattar.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public MarketData get() {
      return restTemplate.getForObject(url, MarketData.class);
    }
}
