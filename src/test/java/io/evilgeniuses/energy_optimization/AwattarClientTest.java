package io.evilgeniuses.energy_optimization;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AwattarClientTest {

    @Autowired
    AwattarClient awattarClient;

    @MockBean
    RestTemplate restTemplate;

    @Value("${awattar.url}")
    String url;


    @Test
    void get() {
        awattarClient.get();
        Mockito.verify(restTemplate).getForObject(url, MarketData.class);
    }
}