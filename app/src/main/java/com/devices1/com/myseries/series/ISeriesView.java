package com.devices1.com.myseries.series;


import java.util.List;

public interface ISeriesView {

    void showTitle(String title);
    void showSeasons(Integer numberSeasons);
    void showEpisodes(EpisodeData[] episodeData);
    void showSearchInProgress();
}
