package com.devices1.com.myseries.model;


import com.devices1.com.myseries.model.network.ResponseReceiver;

import java.util.List;

public interface ISeriesModel {

    void findSeries(String title, ResponseReceiver<List<SeriesData>> responseReceiver);
    void addSeries(SeriesData list);
    List<Integer> getAllSeries();
    String getSeriesTitle(int id);
    Integer getSeriesNumberOfSeasons(int id);
    List<String> getEpisodeTitles(Integer currentSeries, Integer currentSeason);
    List<Boolean> getEpisodeViewed(Integer currentSeries, Integer currentSeason);
    void setEpisodeViewed(Integer currentSeries, Integer currentSeason, int episode, boolean viewed);
}
