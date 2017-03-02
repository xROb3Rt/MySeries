package com.devices1.com.myseries.model;

import java.util.Comparator;

/*
   The information available for a series.
 */
public class SeriesData {
    public static final Comparator<? super SeriesData> NAME_COMPARATOR = new Comparator<SeriesData>() {
        @Override
        public int compare(SeriesData left, SeriesData right) {
            return left.getTitle().compareTo(right.getTitle());
        }
    };
    private int id;
    private String title;
    private String summary;
    private String firstAired;
    private String network;
    private String status;
    private int numberOfSeasons;

    public SeriesData(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public SeriesData copy() {
        SeriesData other = new SeriesData(id);
        other.title = title;
        other.summary = summary;
        other.firstAired = firstAired;
        other.network = network;
        other.status = status;
        other.numberOfSeasons = numberOfSeasons;

        return other;
    }

    @Override
    public String toString() {
        return title;
    }
}
