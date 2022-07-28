package io.evilgeniuses.energy_optimization;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {

    private final ConsumptionCalculator consumption;
    private final ConsumptionPriceCalculator price;

    private final FrontendDataManager manager;

    private final ForecastManager forecastManager;




    public UIController(ConsumptionCalculator consumption, ConsumptionPriceCalculator price, FrontendDataManager manager, ForecastManager forecastManager) {
        this.consumption = consumption;
        this.price = price;
        this.manager = manager;
        this.forecastManager = forecastManager;
    }

    @GetMapping
    public String loadTableUI(Model model, @RequestParam String source) {
        model.addAttribute("months", manager.getMonths(source));
        //create new method to get all the stuff
        model.addAttribute("previous", source);


        return "mainpagetable";
    }

    @GetMapping("/forecast")
    public String loadForecastUI(Model model, @RequestParam String source) {
        model.addAttribute("datapoints", manager.getForecast(source));
        model.addAttribute("previous", source);

        return "forecast";
    }
}
