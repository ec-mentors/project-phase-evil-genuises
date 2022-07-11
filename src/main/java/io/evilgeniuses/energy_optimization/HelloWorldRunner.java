package io.evilgeniuses.energy_optimization;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldRunner {

    @Bean
    ApplicationRunner runner(ItemRepository itemRepository) {
        return args -> {
            if (itemRepository.findAll().isEmpty()) {
                itemRepository.save(new Item("Hello World"));
            }
        };
    }
}
