package com.devices1.com.myseries.series;


public class EpisodeData {


    private String title;
    private Boolean viewed;

    public EpisodeData(String title, Boolean viewed) {

        this.title = title;
        this.viewed = viewed;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public String getTitle() {

        return title;
    }
}
