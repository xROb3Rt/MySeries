package com.devices1.com.myseries.model.database;

import com.devices1.com.myseries.model.SeasonData;
import com.devices1.com.myseries.model.SeriesData;

import java.util.List;

/*
    The interface for the database storing the series and the episodes viewed.
 */

public interface ISeriesDB {
    List<Integer> getAllSeries();

    void insertSeriesData(SeriesData seriesData);
    SeriesData getSeriesData(int id);

    void insertSeasonData(int id, SeasonData seasonData);
    SeasonData getSeasonData(int id, int seasonNumber);

    void setViewed(int id, int seasonNumber, int episode, boolean viewed);
}
