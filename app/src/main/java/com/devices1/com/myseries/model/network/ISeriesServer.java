package com.devices1.com.myseries.model.network;



import com.devices1.com.myseries.model.SeasonData;
import com.devices1.com.myseries.model.SeriesData;

import java.util.List;

/*
    The interface for accessing the servers.
 */

public interface ISeriesServer {
    void findSeries(String title, ResponseReceiver<List<SeriesData>> responseReceiver);
    void findSeason(int id, int seasonNumber, ResponseReceiver<SeasonData> responseReceiver);
    void findSeasons(int id, ResponseReceiver<Integer> responseReceiver);
}
