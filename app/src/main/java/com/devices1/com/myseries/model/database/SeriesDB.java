package com.devices1.com.myseries.model.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devices1.com.myseries.model.EpisodeInfo;
import com.devices1.com.myseries.model.SeasonData;
import com.devices1.com.myseries.model.SeriesData;

import java.util.ArrayList;
import java.util.List;

public class SeriesDB extends SQLiteOpenHelper implements ISeriesDB {

    public static final String DATABASE_NAME = "series.db";
    public static final int DATABASE_VERSION = 1;
    public static final String SERIES = "SERIES";
    public static final String SERIES_ID = "ID";
    public static final String TEXT_NOT_NULL_UNIQUE = "text not null unique";
    public static final String SERIES_TITLE = "TITLE";
    public static final String TEXT_NOT_NULL = "text not null";
    public static final String SERIES_SUMMARY = "SUMMARY";
    public static final String TEXT = "text";
    public static final String SERIES_FIRSTAIRED = "FIRSTAIRED";
    public static final String SERIES_NETWORK = "NETWORK";
    public static final String SERIES_STATUS = "STATUS";
    public static final String SERIES_SEASONS = "SEASONS";
    public static final String INTEGER_NOT_NULL = "integer not null";
    public static final String EPISODES = "EPISODES";
    public static final String EPISODES_SERIES_ID = "SERIES_ID";
    public static final String EPISODES_SEASON = "SEASON";
    public static final String EPISODES_NUMBER = "NUMBER";
    public static final String EPISODES_SUMMARY = "SUMMARY";
    public static final String INTEGER = "integer";
    public static final String EPISODES_TITLE = "TITLE";
    public static final String EPISODES_VIEWED = "VIEWED";
    public static final String FOREIGN_KEY = "foreign key";
    public static final String REFERENCES = "references";
    public static final String PRIMARY_KEY = "primary key";

    public SeriesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createSeriesTable(sqLiteDatabase);
        createEpisodeTable(sqLiteDatabase);
    }

    private void createEpisodeTable(SQLiteDatabase sqLiteDatabase) {
        String create = "create table " + EPISODES + "("
                + EPISODES_SERIES_ID + " " + INTEGER_NOT_NULL + ","
                + EPISODES_SEASON + " " + INTEGER_NOT_NULL + ","
                + EPISODES_NUMBER + " " + INTEGER + ","
                + EPISODES_TITLE + " " + TEXT + ","
                + EPISODES_VIEWED + " " + INTEGER_NOT_NULL + ","
                + EPISODES_SUMMARY + " " + TEXT + ","
                + FOREIGN_KEY + "( " + EPISODES_SERIES_ID + ")" + REFERENCES + " SERIES(" + SERIES_ID + "),"
                + PRIMARY_KEY + "(" + EPISODES_SERIES_ID + "," + EPISODES_SEASON + "," + EPISODES_NUMBER + "));";
        sqLiteDatabase.execSQL(create);
    }

    private void createSeriesTable(SQLiteDatabase sqLiteDatabase) {
        String create = "create table " + SERIES + "("
                + SERIES_ID + " " + TEXT_NOT_NULL_UNIQUE + ","
                + SERIES_TITLE + " " + TEXT_NOT_NULL + ","
                + SERIES_SUMMARY + " " + TEXT + ","
                + SERIES_FIRSTAIRED + " " + TEXT + ","
                + SERIES_NETWORK + " " + TEXT + ","
                + SERIES_STATUS + " " + TEXT + ","
                + SERIES_SEASONS + " " + INTEGER_NOT_NULL + ");";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists " + SERIES);
        sqLiteDatabase.execSQL("drop table if exists " + EPISODES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public List<Integer> getAllSeries() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(SERIES,new String[]{SERIES_ID},
                null,
                null,
                null,
                null,
                SERIES_TITLE);
        List<Integer> listAllSeries = new ArrayList<>();
        while(cursor.moveToNext()){
            int n = cursor.getInt(0);
            listAllSeries.add(n);
        }

        cursor.close();
        return listAllSeries;
    }

    @Override
    public void insertSeriesData(SeriesData seriesData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERIES_ID, seriesData.getId());
        contentValues.put(SERIES_TITLE, seriesData.getTitle());
        contentValues.put(SERIES_SUMMARY,seriesData.getSummary());
        contentValues.put(SERIES_FIRSTAIRED,seriesData.getFirstAired());
        contentValues.put(SERIES_NETWORK,seriesData.getNetwork());
        contentValues.put(SERIES_STATUS,seriesData.getStatus());
        contentValues.put(SERIES_SEASONS,seriesData.getNumberOfSeasons());
        db.insertWithOnConflict(SERIES,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public SeriesData getSeriesData(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(SERIES,null,
                SERIES_ID + "=?",
                new String[]{Integer.toString(id)},
                null,
                null,
                SERIES_TITLE);
        if(!cursor.moveToNext()){
            cursor.close();
            return null;
        }
        else{
            SeriesData seriesData = new SeriesData(id);
            seriesData.setId(cursor.getInt(0));
            seriesData.setTitle(cursor.getString(1));
            seriesData.setSummary(cursor.getString(2));
            seriesData.setFirstAired(cursor.getString(3));
            seriesData.setNetwork(cursor.getString(4));
            seriesData.setStatus(cursor.getString(5));
            seriesData.setNumberOfSeasons(cursor.getInt(6));

            cursor.close();
            return seriesData;
        }
    }

    @Override
    public void insertSeasonData(int id, SeasonData seasonData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EPISODES_SERIES_ID,id);
        contentValues.put(EPISODES_SEASON,seasonData.getNumber());
        for(EpisodeInfo episodeInfo : seasonData.getEpisodes()){
            contentValues.put(EPISODES_NUMBER,episodeInfo.getNumber());
            contentValues.put(EPISODES_TITLE,episodeInfo.getTitle());
            contentValues.put(EPISODES_VIEWED,episodeInfo.isViewed());
            contentValues.put(EPISODES_SUMMARY, episodeInfo.getSummary());
            db.insertWithOnConflict(EPISODES,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        }
    }

    @Override
    public SeasonData getSeasonData(int id, int seasonNumber) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(EPISODES,new String[]{EPISODES_NUMBER,EPISODES_TITLE,EPISODES_VIEWED,EPISODES_SUMMARY},
                EPISODES_SERIES_ID + "=? AND " + EPISODES_SEASON + "=?",
                new String[]{Integer.toString(id),Integer.toString(seasonNumber)},
                null,
                null,
                EPISODES_NUMBER);
        if(cursor.getCount() == 0){
            cursor.close();
            return null;
        }
        else{
            SeasonData seasonData = new SeasonData(seasonNumber);
            while(cursor.moveToNext()){
                int number = cursor.getInt(0);
                String title = cursor.getString(1);
                int viewed = cursor.getInt(2);
                String summary = cursor.getString(3);
                EpisodeInfo info = new EpisodeInfo(number, title, summary);
                info.setViewed(viewed==1);
                seasonData.addEpisode(info);
            }

            cursor.close();
            return seasonData;
        }
    }

    @Override
    public void setViewed(int id, int seasonNumber, int episode, boolean viewed) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EPISODES_VIEWED,viewed);
        db.update(EPISODES,contentValues,
                EPISODES_SERIES_ID + "= ? AND " + EPISODES_SEASON
                        +"= ? AND " + EPISODES_NUMBER + " = ? ",
                new String[]{Integer.toString(id),
                        Integer.toString(seasonNumber),
                        Integer.toString(episode)});
    }

}
