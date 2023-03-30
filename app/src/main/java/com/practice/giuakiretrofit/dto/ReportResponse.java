package com.practice.giuakiretrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReportResponse {
    @SerializedName("columnData")
    private Map<String, Float> columnData;

    @SerializedName("max")
    private Double max;

    @SerializedName("min")
    private Double min;

    @SerializedName("average")
    private Double average;

    @SerializedName("total")
    private Double total;

    @SerializedName("consumedHistory")
    private List<FoodTrackingHistory> consumedHistory;

    public ReportResponse(Map<String, Float> columnData, Double max, Double min, Double average, Double total, List<FoodTrackingHistory> consumedHistory) {
        this.columnData = columnData;
        this.max = max;
        this.min = min;
        this.average = average;
        this.total = total;
        this.consumedHistory = consumedHistory;
    }

    public Map<String, Float> getColumnData() {
        return columnData;
    }

    public void setColumnData(Map<String, Float> columnData) {
        this.columnData = columnData;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<FoodTrackingHistory> getConsumedHistory() {
        return consumedHistory;
    }

    public void setConsumedHistory(List<FoodTrackingHistory> consumedHistory) {
        this.consumedHistory = consumedHistory;
    }
}
