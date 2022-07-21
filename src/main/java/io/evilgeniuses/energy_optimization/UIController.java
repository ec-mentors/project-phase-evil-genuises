package io.evilgeniuses.energy_optimization;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {

    ConsumptionCalculator consumption;
    ConsumptionPriceCalculator price;

    FrontendDataManager manager;


    public UIController(ConsumptionCalculator consumption, ConsumptionPriceCalculator price, FrontendDataManager manager) {
        this.consumption = consumption;
        this.price = price;
        this.manager = manager;
    }

    @GetMapping
    public String loadTableUI(Model model, @RequestParam String input) {
        model.addAttribute("months", manager.getMonths(input));
        //create new method to get all the stuff
        model.addAttribute("previous", input);


        return "mainpagetable";
    }
}
