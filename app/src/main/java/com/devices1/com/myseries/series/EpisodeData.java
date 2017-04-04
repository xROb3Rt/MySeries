package com.devices1.com.myseries.series;


public class EpisodeData {


    private String title;
    private Boolean viewed;
    private String summary;

    public EpisodeData(String title, Boolean viewed, String summary) {

        this.title = title;
        this.viewed = viewed;
        this.summary = summary;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public String getTitle() {
        return title;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public String getSummary(){
        return  summary;
    }
}
