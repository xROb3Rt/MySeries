package com.devices1.com.myseries.seriesDetail;

import com.devices1.com.myseries.model.ISeriesModel;


public class SeriesDetailPresenter {

    private ISeriesDetailView view;
    private ISeriesModel model;

    public SeriesDetailPresenter(ISeriesDetailView view, ISeriesModel model) {

        this.view = view;
        this.model = model;

    }
    public void setSeries(int series) {
        view.showTitle(model.getSeriesTitle(series));
        view.showFirstAired(model.getSeriesFirstAired(series));
        view.showNetwork(model.getSeriesNetwork(series));
        view.showStatus(model.getSeriesStatus(series));
        view.showSummary(model.getSeriesSummary(series));
    }

}
