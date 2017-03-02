package com.devices1.com.myseries.model.database;

import com.jvilaruji.myseries.model.SeasonData;
import com.jvilaruji.myseries.model.SeriesData;

import java.util.ArrayList;
import java.util.List;

/*
    A mock database that stores the contents in static variables, They only last
    as long as the application runs.
 */

public class MockSeriesDB implements ISeriesDB {
    private static List<SeriesAndSeasons> knownSeries = new ArrayList<>();

    @Override
    public List<Integer> getAllSeries() {
        List<Integer> ids = new ArrayList<>(knownSeries.size());
        for (SeriesAndSeasons seriesAndSeasons : knownSeries)
            ids.add(seriesAndSeasons.getId());
        return ids;
    }

    @Override
    public SeriesData getSeriesData(int id) {
        SeriesAndSeasons seriesAndSeasons = findSeries(id);
        return seriesAndSeasons != null ? seriesAndSeasons.getSeriesData() : null;
    }

    private SeriesAndSeasons findSeries(int id) {
        for (SeriesAndSeasons seriesAndSeasons : knownSeries)
            if (seriesAndSeasons.getId() == id)
                return seriesAndSeasons;
        return null;
    }

    @Override
    public void insertSeasonData(int id, SeasonData seasonData) {
        SeriesAndSeasons seriesAndSeasons = findSeries(id);
        seriesAndSeasons.insertSeasonData(seasonData);
    }

    @Override
    public SeasonData getSeasonData(int id, int seasonNumber) {
        SeriesAndSeasons seriesAndSeasons = findSeries(id);
        return seriesAndSeasons.getSeason(seasonNumber);
    }

    @Override
    public void insertSeriesData(SeriesData seriesData) {
        for (SeriesAndSeasons seriesAndSeasons: knownSeries)
            if (seriesAndSeasons.getId() == seriesData.getId()) {
                seriesAndSeasons.setSeriesData(seriesData);
                return;
            }
        knownSeries.add(new SeriesAndSeasons(seriesData));
    }

    @Override
    public void setViewed(int id, int seasonNumber, int episode, boolean viewed) {
        SeasonData seasonData = null;
        for (SeriesAndSeasons seriesAndSeasons : knownSeries)
            if (seriesAndSeasons.getId() == id)
                seasonData = seriesAndSeasons.getSeason(seasonNumber);
        seasonData.getEpisodes().get(episode).setViewed(viewed);
    }
}
