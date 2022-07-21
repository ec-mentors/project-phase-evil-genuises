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

    //DEPRECATED DONT USE
    @GetMapping("/oldui")
    public String loadUI(Model model, @RequestParam int input) {
        if (input < 13) {
            model.addAttribute("result",
                    String.valueOf(consumption.getConsumptionPerMonth(input, "CSV1")) + " kWh / " + price.getMonthlyPerFixedPrice(input,
                            "CSV1") + " €");
        } else {
            double wholeYearKWH = 0;
            for (int i = 1; i < 13; i++) {
                wholeYearKWH += consumption.getConsumptionPerMonth(i, "CSV1");
            }

            double wholeYearPrice = 0;
            for (double price : price.getPriceForEveryMonth("CSV1")) {
                wholeYearPrice += price;
            }
            model.addAttribute("result", String.valueOf(wholeYearKWH + " kWh / " + wholeYearPrice + " €"));
        }
        model.addAttribute("previous", input);
        //, @RequestParam int month .
        return "mainpage";
    }

    @GetMapping
    public String loadTableUI(Model model, @RequestParam String input) {
        model.addAttribute("months", manager.getMonths(input));
        //create new method to get all the stuff
        model.addAttribute("previous", input);


        return "mainpagetable";
    }
}
