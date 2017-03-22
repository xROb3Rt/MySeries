package com.devices1.com.myseries.series;


import com.devices1.com.myseries.model.ISeriesModel;
import com.devices1.com.myseries.model.SeriesModel;
import com.devices1.com.myseries.model.network.ResponseReceiver;

import java.util.ArrayList;
import java.util.List;

public class SeriesPresenter {

    ISeriesView view;
    ISeriesModel model;

    private int season;
    private Integer currentSeries;
    private String title;
    private Integer numberSeasons;
    private Integer currentSeason;

    public SeriesPresenter(SeriesActivity view, SeriesModel model) {
        this.view = view;
        this.model = model;
    }

    public void onAddSeasonRequested() {

        view.showSearchInProgress(); // MO ESTÁ HECHo EL MÉTODO!!!


        /*model.updateSeasons(currentSeries, new ResponseReceiver<Integer>() {
            @Override
            public void onResponseReceived(Integer numberOfSeasons) {
                view.showSeasons(numberOfSeasons);
                if(currentSeason != -1)
                  model.updateSeasonData(currentSeries, currentSeason, new ResponseReceiver<Void>() {
                      @Override public void onResponseReceived(Void response) {
                          // AREA A
                      }
                      @Override public void onErrorReceived(String message) {
                          // AREA B
                      }
                  });
            }
            @Override public void onErrorReceived(String message) {
                // AREA C
            }
        });
        */

    }


    public void setSeries(int series) {
        currentSeries = series;
        title = model.getSeriesTitle(currentSeries);
        view.showTitle(title);
        numberSeasons = model.getSeriesNumberOfSeasons(series);
        view.showSeasons(numberSeasons);
    }

    public void setSeason(int currentSeason){

        this.currentSeason = currentSeason;
        List<String> titles = model.getEpisodeTitles(currentSeries, currentSeason);
        List<Boolean> viewed = model.getEpisodeViewed(currentSeries, currentSeason);

        EpisodeData[] episodeData = new EpisodeData[titles.size() - 1];

        for(int i = 0; i<titles.size(); i++){
            episodeData[i] = new EpisodeData(titles.get(i), viewed.get(i));
        }

        view.showEpisodes(episodeData);

    }

    public void onEpisodeViewedChanged(int episode, boolean viewed) {
        model.setEpisodeViewed(currentSeries, currentSeason, episode, viewed);
    }
}
