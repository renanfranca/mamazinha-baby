package com.mamazinha.baby.service.dto;

public class NapHumorDTO {

    private Integer dayOfWeek;

    private Integer humorAverage;

    public Integer getHumorAverage() {
        return humorAverage;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public NapHumorDTO dayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setHumorAverage(Integer humorAverage) {
        this.humorAverage = humorAverage;
    }

    public NapHumorDTO humorAverage(Integer humorAverage) {
        this.humorAverage = humorAverage;
        return this;
    }
}
