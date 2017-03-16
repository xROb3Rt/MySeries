package com.devices1.com.myseries.model.database;

import android.support.annotation.NonNull;

import com.devices1.com.myseries.model.SeasonData;
import com.devices1.com.myseries.model.SeriesData;

import java.util.ArrayList;
import java.util.List;

/*
   A class for collecting the data about a series and the
   seasons associated to it.
 */

public class SeriesAndSeasons {
    private SeriesData seriesData;
    private List<SeasonData> seasons;

    public SeriesAndSeasons(SeriesData seriesData) {
        this.seriesData = seriesData;
        seasons = new ArrayList<>();
    }

    public int getId() {
        return seriesData.getId();
    }

    public SeasonData getSeason(int seasonNumber) {
        for (SeasonData info: seasons)
            if (info.getNumber() == seasonNumber)
                return info;
        return null;
    }

    public SeasonData createSeason(int seasonNumber) {
        SeasonData info = getSeason(seasonNumber);
        if (info != null)
            return info;
        info = new SeasonData(seasonNumber);
        seasons.add(info);
        return info;
    }

    public void insertSeasonData(@NonNull SeasonData seasonData) {
        for (int i = 0 ; i < seasons.size() ; i++)
            if (seasons.get(i).getNumber() == seasonData.getNumber()){
                seasons.set(i, seasonData);
                return;
            }
        seasons.add(seasonData);
    }

    public SeriesData getSeriesData() {
        return seriesData;
    }

    public String getTitle() {
        return seriesData.getTitle();
    }

    public void setSeriesData(SeriesData seriesData) {
        this.seriesData = seriesData;
    }
}
