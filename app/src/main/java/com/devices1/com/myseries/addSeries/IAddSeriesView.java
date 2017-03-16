package com.devices1.com.myseries.addSeries;

import java.util.List;

public interface IAddSeriesView {

    void showTitle(String title);
    void showSearchInProgress();

    void hideSearchInProgress();
    void showError(String message);

    void showTitles(List<String> titles);

    void askConfirmation(String title, String firstAired, String summary);
    void addSeries(int index);
}
