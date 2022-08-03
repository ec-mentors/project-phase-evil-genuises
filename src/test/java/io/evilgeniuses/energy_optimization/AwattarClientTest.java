package io.evilgeniuses.energy_optimization;

import io.evilgeniuses.energy_optimization.dataclasses.MarketData;
import io.evilgeniuses.energy_optimization.parsing.AwattarClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AwattarClientTest {

    @Autowired
    AwattarClient awattarClient;

    @MockBean
    RestTemplate restTemplate;

    @Value("${awattar.url_2019}")
    String url;


    @Test
    void get() {
        awattarClient.getDataFromYear2019();
        Mockito.verify(restTemplate).getForObject(url, MarketData.class);
    }
}