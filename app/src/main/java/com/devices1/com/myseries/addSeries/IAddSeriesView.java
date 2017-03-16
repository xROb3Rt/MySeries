package com.devices1.com.myseries.addSeries;

import com.devices1.com.myseries.model.SeriesData;

import java.util.List;

public interface IAddSeriesView {

    void showTitle(String title);
    void showSearchInProgress();

    void hideSearchInProgress();
    void showError(String message);

    void showTitles(List<String> titles);

    void askConfirmation(String title, String firstAired, String summary);
    void switchToMySeries();
}
