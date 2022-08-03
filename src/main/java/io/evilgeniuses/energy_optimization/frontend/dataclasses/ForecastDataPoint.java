package io.evilgeniuses.energy_optimization.frontend.dataclasses;

public class ForecastDataPoint {

    private String endTimeStamp, consumptionInKWH, pricePerKWH, billingAmount;

    public ForecastDataPoint(String endTimeStamp, String consumptionInKWH, String pricePerKWH, String billingAmount) {
        this.endTimeStamp = endTimeStamp;
        this.consumptionInKWH = consumptionInKWH;
        this.pricePerKWH = pricePerKWH;
        this.billingAmount = billingAmount;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getConsumptionInKWH() {
        return consumptionInKWH;
    }

    public void setConsumptionInKWH(String consumptionInKWH) {
        this.consumptionInKWH = consumptionInKWH;
    }

    public String getPricePerKWH() {
        return pricePerKWH;
    }

    public void setPricePerKWH(String pricePerKWH) {
        this.pricePerKWH = pricePerKWH;
    }

    public String getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(String billingAmount) {
        this.billingAmount = billingAmount;
    }
}
