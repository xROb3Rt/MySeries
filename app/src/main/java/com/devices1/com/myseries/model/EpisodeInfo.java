package com.devices1.com.myseries.model;

public class EpisodeInfo {

    private boolean viewed;
    private final int number;
    private final String title;
    private final String summary;

    public EpisodeInfo(int number, String title, String summary) {
        this.title = title;
        this.number = number;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public int getNumber() {
        return number;
    }

    public String getSummary() {
        return summary;
    }
}
