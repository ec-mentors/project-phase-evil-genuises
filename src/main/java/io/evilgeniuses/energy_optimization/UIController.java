package io.evilgeniuses.energy_optimization;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UIController {

    ConsumptionCalculator consumption;
    ConsumptionPriceCalculator price;


    public UIController(ConsumptionCalculator consumption, ConsumptionPriceCalculator price) {
        this.consumption = consumption;
        this.price = price;
    }

    @GetMapping
    public String loadUI(Model model, @RequestParam int input) {
        if (input < 13) {
            model.addAttribute("result", String.valueOf(consumption.getConsumptionPerMonth(input, 1)) + " kWh / " + price.getMonthlyPrice(input, 1) + " €");
        } else {
            double wholeYearKWH = 0;
            for (int i = 1; i < 13; i++) {
                wholeYearKWH += consumption.getConsumptionPerMonth(i, 1);
            }

            double wholeYearPrice = 0;
            for (double price : price.getPriceForEveryMonth(1)) {
                wholeYearPrice += price;
            }
            model.addAttribute("result", String.valueOf(wholeYearKWH + " kWh / "+ wholeYearPrice + " €"));
        }
        model.addAttribute("previous", input);
        //, @RequestParam int month
        return "mainpage";
    }
}
