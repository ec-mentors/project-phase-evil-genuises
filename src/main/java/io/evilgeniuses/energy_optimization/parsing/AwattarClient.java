package io.evilgeniuses.energy_optimization.parsing;

import io.evilgeniuses.energy_optimization.dataclasses.MarketData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class AwattarClient {

    private final RestTemplate restTemplate;
    private final String url2019;
    private final String urlFuture;

    public AwattarClient(RestTemplate restTemplate, @Value("${awattar.url_2019}") String url2019,
                         @Value("${awattar.url_future}") String urlFuture) {
        this.restTemplate = restTemplate;
        this.url2019 = url2019;
        this.urlFuture = urlFuture;
    }

    public MarketData getDataFromYear2019() {
        return restTemplate.getForObject(url2019, MarketData.class);
    }
    public MarketData getDataFromFuture() {
        return restTemplate.getForObject(urlFuture, MarketData.class);
    }
}
