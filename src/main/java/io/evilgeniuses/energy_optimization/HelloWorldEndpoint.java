package io.evilgeniuses.energy_optimization;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class HelloWorldEndpoint {

    private final ItemRepository repository;

    public HelloWorldEndpoint(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    String getText() {
        var text = repository.findAll();
        return text.stream().map(Item::getText).collect(Collectors.joining());
    }
}
