package com.devices1.com.myseries.seriesDetail;

/**
 * Created by BorisGoshev on 28/03/2017.
 */

public interface ISeriesDetailView {

    void showTitle(String title);
    void showSummary(String summary);
    void showFirstAired(String firstAired);
    void showNetwork(String network);
    void showStatus(String status);

}
