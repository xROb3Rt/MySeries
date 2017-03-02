package com.devices1.com.myseries.model;

import java.util.ArrayList;
import java.util.List;

/*
   The information associated to a season.
 */
public class SeasonData {
    private int number;
    private List<EpisodeInfo> episodes;

    public SeasonData(int number) {
        this.number = number;
        episodes = new ArrayList<>();
    }

    public void addEpisode(EpisodeInfo info){
        episodes.add(info);
    }

    public int getNumber() {
        return number;
    }

    public List<EpisodeInfo> getEpisodes() {
        return episodes;
    }
}
