package com.devices1.com.myseries.model;

public class EpisodeInfo {
    private boolean viewed;
    private final int number;
    private final String title;

    public EpisodeInfo(int number, String title) {
        this.title = title;
        this.number = number;
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
}
