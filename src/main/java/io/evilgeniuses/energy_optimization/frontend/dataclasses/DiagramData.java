package io.evilgeniuses.energy_optimization.frontend.dataclasses;

public class DiagramData {

   private int hour;
   private double kwh;
   private double price;

    public DiagramData(int hour, double kwh) {
        this.hour = hour;
        this.kwh = kwh;
    }

    public DiagramData(int hour, double kwh, double price) {
        this.hour = hour;
        this.kwh = kwh;
        this.price = price;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
