package com.devices1.com.myseries.model;


import android.content.Context;

import com.devices1.com.myseries.model.database.ISeriesDB;
import com.devices1.com.myseries.model.database.MockSeriesDB;
import com.devices1.com.myseries.model.network.ISeriesServer;
import com.devices1.com.myseries.model.network.MockSeriesServer;
import com.devices1.com.myseries.model.network.ResponseReceiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeriesModel implements ISeriesModel {

    private ISeriesServer server;
    private ISeriesDB db;
    private static SeriesModel instance = null;

    public static SeriesModel getInstance() {
        return instance;
    }

    public static SeriesModel getInstance(Context context) {
        if (instance == null)
            instance = new SeriesModel( new MockSeriesServer(), new MockSeriesDB());
        return instance;
    }

    private SeriesModel(ISeriesServer server, ISeriesDB db) {
        this.server = server;
        this.db = db;
    }

    @Override
    public void findSeries(String title, final ResponseReceiver<List<SeriesData>> responseReceiver) {
        server.findSeries(title,
                new
                        ResponseReceiver<List<SeriesData>>() {
                            @Override
                            public void
                            onResponseReceived(List<SeriesData> series) {
                                Collections.sort(series, SeriesData.NAME_COMPARATOR);
                                responseReceiver.onResponseReceived(series);
                            }
                            @Override
                            public void
                            onErrorReceived(String message) {
                                responseReceiver.onErrorReceived(message);
                            }
                        });
    }

    @Override
    public void addSeries(SeriesData seriesData) {
        db.insertSeriesData(seriesData);
    }

    @Override
    public List<Integer> getAllSeries() {
        return db.getAllSeries();
    }

    @Override
    public String getSeriesTitle(int id) {
        SeriesData seriesData = db.getSeriesData(id);
        return seriesData.getTitle();
    }

    @Override
    public Integer getSeriesNumberOfSeasons(int id) {
        SeriesData seriesData = db.getSeriesData(id);
        return seriesData.getNumberOfSeasons();
    }

    @Override
    public List<String> getEpisodeTitles(Integer currentSeries, Integer currentSeason) {
        List<String> titles = new ArrayList<>();
        SeasonData seasonData = db.getSeasonData(currentSeries, currentSeason);
        if (seasonData == null)
            return titles;
        List<EpisodeInfo> episodeInfos = seasonData.getEpisodes();
        for (EpisodeInfo episodeInfo: episodeInfos){
            titles.add(episodeInfo.getTitle());
        }
        return titles;
    }

    @Override
    public List<Boolean> getEpisodeViewed(Integer currentSeries, Integer currentSeason) {

        List<Boolean> episodeViewed = new ArrayList<>();
        SeasonData seasonData = db.getSeasonData(currentSeries, currentSeason);
        if (seasonData == null)
            return episodeViewed;


        List<EpisodeInfo> episodeInfos = seasonData.getEpisodes();
        for (EpisodeInfo episodeInfo: episodeInfos){
            episodeViewed.add(episodeInfo.isViewed());
        }
        return episodeViewed;
    }

    @Override
    public void setEpisodeViewed(Integer currentSeries, Integer currentSeason, int episode, boolean viewed) {
        db.setViewed(currentSeries, currentSeason, episode, viewed);
    }

    @Override
    public void updateSeasons(final Integer currentSeries, final ResponseReceiver<Integer> responseReceiver) {

        server.findSeasons(currentSeries, responseReceiver);

    }

    @Override
    public void updateSeasonData(final Integer currentSeries, Integer currentSeason, final ResponseReceiver<SeasonData> responseReceiver) {

        server.findSeason(currentSeries, currentSeason, responseReceiver);

    }

    @Override
    public String getSeriesSummary(int id) {
        SeriesData seriesData = db.getSeriesData(id);
        return seriesData.getSummary();
    }
    @Override
    public String getSeriesFirstAired(int id) {
        SeriesData seriesData = db.getSeriesData(id);
        return seriesData.getFirstAired();
    }
    @Override
    public String getSeriesNetwork(int id) {
        SeriesData seriesData = db.getSeriesData(id);
        return seriesData.getNetwork();
    }
    @Override
    public String getSeriesStatus(int id) {
        SeriesData seriesData = db.getSeriesData(id);
        return seriesData.getStatus();
    }

}
