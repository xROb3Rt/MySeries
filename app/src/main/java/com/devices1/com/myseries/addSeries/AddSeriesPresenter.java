package com.devices1.com.myseries.addSeries;


import com.devices1.com.myseries.model.ISeriesModel;
import com.devices1.com.myseries.model.SeriesData;
import com.devices1.com.myseries.model.network.ResponseReceiver;

import java.util.ArrayList;
import java.util.List;

public class AddSeriesPresenter {

    private String searchedTitle;
    private IAddSeriesView view;
    private ISeriesModel model;
    private List<SeriesData> seriesList;
    private int requestedIndex;

    public AddSeriesPresenter(IAddSeriesView view, ISeriesModel model) {
        this.view = view;
        this.model = model;
    }

    public void setSearchedTitle(String searchedTitle) {
        this.searchedTitle = searchedTitle;
        view.showTitle(searchedTitle);
        view.showSearchInProgress();
        model.findSeries(searchedTitle,
                new ResponseReceiver<List<SeriesData>>() {
                    @Override
                    public void onResponseReceived(List<SeriesData> series) {
                        view.hideSearchInProgress();
                        seriesFound(series);
                    }

                    @Override
                    public void onErrorReceived(String message) {
                        view.hideSearchInProgress();
                        view.showError(message);
                        }
                });

    }

    public void seriesFound(List<SeriesData> series) {

        seriesList = series;

        List<String> titles = new ArrayList<String>();

        for (int i = 0; i< series.size(); i++){
            titles.add(series.get(i).getTitle());
        }

        view.showTitles(titles);

    }

    public void onAddSeriesRequested(int i){

        requestedIndex = i;
        SeriesData seriesData = seriesList.get(i);
        view.askConfirmation(seriesData.getTitle(), seriesData.getFirstAired(), seriesData.getSummary());

    }

    public void onAddSeriesConfirmed() {

        model.addSeries(seriesList.get(requestedIndex));
        view.switchToMySeries();

    }
}
