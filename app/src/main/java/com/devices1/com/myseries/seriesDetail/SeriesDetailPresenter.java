package com.devices1.com.myseries.seriesDetail;

import com.devices1.com.myseries.model.ISeriesModel;

/**
 * Created by BorisGoshev on 28/03/2017.
 */

public class SeriesDetailPresenter {

    private ISeriesDetailView view;
    private ISeriesModel model;
    private int series;

    public SeriesDetailPresenter(ISeriesDetailView view, ISeriesModel model) {

        this.view = view;
        this.model = model;

    }
    public void setSeries(int series) {

        String summary = model.getSeriesSummary(series);
        view.showSummary(summary);

        String firstAired = model.getSeriesFirstAired(series);
        view.showFirstAired(firstAired);

        String network = model.getSeriesNetwork(series);
        view.showNetwork(network);

        String status = model.getSeriesStatus(series);
        view.showStatus(status);
    }

}
