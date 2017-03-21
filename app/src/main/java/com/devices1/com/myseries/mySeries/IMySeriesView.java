package com.devices1.com.myseries.mySeries;


import java.util.List;

public interface IMySeriesView {
    void switchToAddSeries(String name);
    void displayTitles(List<String> titleList);
    void switchToSeries(Integer index);

}
