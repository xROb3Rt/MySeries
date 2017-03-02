package com.devices1.com.myseries.addSeries;

import com.devices1.com.myseries.model.SeriesData;

import java.util.List;

public interface IAddSeriesView {

    void showTitle(String title);
    void showSearchInProgress();

    void hideSearchInProgress();
    void showError(String message);
    //void seriesFound(List<SeriesData> series);
}
