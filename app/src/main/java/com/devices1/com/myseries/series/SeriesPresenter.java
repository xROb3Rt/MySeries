package com.devices1.com.myseries.series;


import com.devices1.com.myseries.model.ISeriesModel;
import com.devices1.com.myseries.model.SeasonData;
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
    private int numberSeasons;
    private int currentSeason;

    public SeriesPresenter(SeriesActivity view, SeriesModel model) {
        this.view = view;
        this.model = model;
        currentSeason = -1;
    }

    public void onAddSeasonRequested() {

        view.showSearchInProgress();

        model.updateSeasons(currentSeries, new ResponseReceiver<Integer>() {
            @Override
            public void onResponseReceived(final Integer numberOfSeasons) {
                view.showSeasons(numberOfSeasons);
                if(currentSeason != -1)
                  model.updateSeasonData(currentSeries, currentSeason, new ResponseReceiver<SeasonData>() {
                      @Override
                      public void onResponseReceived(SeasonData response) {
                          view.hideSearchInProgress();
                          setSeason(numberOfSeasons);
                      }

                      @Override
                      public void onErrorReceived(String message) {
                          view.hideSearchInProgress();
                          view.showError(message);
                      }
                  });
            }
            @Override public void onErrorReceived(String message) {
                // AREA C
                view.hideSearchInProgress();
                view.showError(message);
            }
        });


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

        EpisodeData[] episodeData = new EpisodeData[titles.size()];

        for(int i = 0; i<titles.size(); i++){
            episodeData[i] = new EpisodeData(titles.get(i), viewed.get(i));
        }

        view.showEpisodes(episodeData);

    }

    public void onEpisodeViewedChanged(int episode, boolean viewed) {
        model.setEpisodeViewed(currentSeries, currentSeason, episode, viewed);
    }

    public void onDetailsRequested() {
        view.switchToSeriesDetails(currentSeries);
    }
}
