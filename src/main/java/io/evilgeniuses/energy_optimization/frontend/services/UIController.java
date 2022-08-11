package io.evilgeniuses.energy_optimization.frontend.services;

import io.evilgeniuses.energy_optimization.frontend.dataclasses.DiagramData;
import io.evilgeniuses.energy_optimization.repositories.EnergyDataPointRepository;
import io.evilgeniuses.energy_optimization.services.ConsumptionCalculator;
import io.evilgeniuses.energy_optimization.services.ConsumptionPriceCalculator;
import io.evilgeniuses.energy_optimization.services.ForecastManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class UIController {

    private final ConsumptionCalculator consumption;
    private final ConsumptionPriceCalculator price;

    private final FrontendDataManager manager;

    private final ForecastManager forecastManager;

    private final EnergyDataPointRepository repository;




    public UIController(ConsumptionCalculator consumption, ConsumptionPriceCalculator price, FrontendDataManager manager, ForecastManager forecastManager, EnergyDataPointRepository repository) {
        this.consumption = consumption;
        this.price = price;
        this.manager = manager;
        this.forecastManager = forecastManager;
        this.repository = repository;
    }

    @GetMapping
    public String loadTableUI(Model model, @RequestParam(defaultValue = "---") String source) {
        model.addAttribute("months", manager.getMonths(source));
        model.addAttribute("options", manager.getSourceKeys());
        //create new method to get all the stuff
        model.addAttribute("previous", source);


        return "mainpagetable";
    }

    @GetMapping("/forecast")
    public String loadForecastUI(Model model, @RequestParam(defaultValue = "---") String source) {
        model.addAttribute("datapoints", manager.getForecast(source));
        model.addAttribute("options", manager.getSourceKeys());
        model.addAttribute("previous", source);
        var dataList = manager.getDiagramData(source);

        Map<Integer, Double> graphData = new TreeMap<>();
        if (!dataList.isEmpty()) {
            for (DiagramData data : dataList) {
                graphData.put(data.getHour(), data.getPrice());
            }
            model.addAttribute("chartData", graphData);
        }

        return "forecast";
    }


//  only for Training!

//    @GetMapping("/chart")
//    public String getPieChart(Model model, @RequestParam(defaultValue = "---") String source) {
//        var dataList = manager.getDiagramData(source);
//
//        Map<Integer, Double> graphData = new TreeMap<>();
//        List<Double> usage = new ArrayList<>();
//        for (DiagramData data : dataList) {
//            graphData.put(data.getHour(), data.getPrice() * data.getKwh());
//            usage.add(data.getKwh());
//        }
//        model.addAttribute("chartData", graphData);
//        return "google-charts";
//    }
}
