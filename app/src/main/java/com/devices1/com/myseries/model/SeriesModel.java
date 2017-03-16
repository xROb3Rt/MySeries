package com.devices1.com.myseries.model;


import android.content.Context;

import com.devices1.com.myseries.model.database.ISeriesDB;
import com.devices1.com.myseries.model.database.MockSeriesDB;
import com.devices1.com.myseries.model.network.ISeriesServer;
import com.devices1.com.myseries.model.network.MockSeriesServer;
import com.devices1.com.myseries.model.network.ResponseReceiver;

import java.util.Collections;
import java.util.List;

public class SeriesModel implements ISeriesModel {

    private ISeriesServer server;
    private ISeriesDB db;
    private static SeriesModel instance = null;
    private List<Integer> seriesIds;

    public static SeriesModel getInstance() {
        return instance;
    }

    public static SeriesModel getInstance(Context context) {
        if (instance == null)
            instance = new SeriesModel( new MockSeriesServer(), new MockSeriesDB());
        return instance;
    }

    private SeriesModel(ISeriesServer server, ISeriesDB db) {
        this.server = server;
        this.db = db;
    }

    @Override
    public void findSeries(String title, final ResponseReceiver<List<SeriesData>> responseReceiver) {
        server.findSeries(title,
                new
                        ResponseReceiver<List<SeriesData>>() {
                            @Override
                            public void
                            onResponseReceived(List<SeriesData> series) {
                                Collections.sort(series, SeriesData.NAME_COMPARATOR);
                                responseReceiver.onResponseReceived(series);
                            }
                            @Override
                            public void
                            onErrorReceived(String message) {
                                responseReceiver.onErrorReceived(message);
                            }
                        });
    }

    @Override
    public void addSeries(SeriesData seriesData) {
        db.insertSeriesData(seriesData);
    }

    @Override
    public List<Integer> getAllSeries() {
        seriesIds = db.getAllSeries();
        return seriesIds;
    }

    @Override
    public String getSeriesTitle(int id) {
        SeriesData seriesData = db.getSeriesData(id);
        return seriesData.getTitle();
    }

}
