package com.devices1.com.myseries.series;


import com.devices1.com.myseries.model.network.ResponseReceiver;

import java.util.List;

public interface ISeriesView {

    void showTitle(String title);
    void showSeasons(Integer numberSeasons);
    void showEpisodes(EpisodeData[] episodeData);

    void showSearchInProgress();
    void hideSearchInProgress();

    void showError(String error);

    void switchToSeriesDetails(int currentSerie);
}
