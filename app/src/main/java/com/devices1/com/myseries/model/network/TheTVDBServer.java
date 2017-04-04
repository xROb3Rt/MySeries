package com.devices1.com.myseries.model.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonWriter;

import com.devices1.com.myseries.model.SeasonData;
import com.devices1.com.myseries.model.SeriesData;
import com.devices1.com.myseries.model.EpisodeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.devices1.com.myseries.model.network.NetworkHelper.downloadUrl;

/**
 * Created by BorisGoshev on 04/04/2017.
 */

//B53993531D04A9CA API

public class TheTVDBServer implements ISeriesServer {

    private static final String APIKey = "B53993531D04A9CA";
    private static final String username = "borisgg";
    private static final String userkey = "7A68BB15812B7B0B";

    private static final String BASE_URL = "https://api.thetvdb.com";
    private static final String LOGIN_URL = BASE_URL + "/login";
    private static final String SEARCH_URL = BASE_URL + "/search/series?name=%s";
    private static final String SEASONS_URL = BASE_URL + "/series/%d/episodes/summary";
    private static final String EPISODES_URL = BASE_URL + "/series/%d/episodes/query?airedSeason=%d";
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";
    private static final String AUTHORIZATION = "Authorization";
    private static final String ACCEPT_LANGUAGE = "Accept-Language";

    private static final String NETWORK_NOT_CONNECTED = "Network not connected";
    private static final String TOKEN_ERROR = "I could not get a token from the server";
    private static final String NETWORK_ERROR = "Network error";
    private static final String BAD_CODE_FROM_SERVER = "Bad code from server";
    private static final String BAD_JSON_IN_SERVER_RESPONSE = "Bad JSON in server response";

    private static final String BEARER = "Bearer ";

    private String JWTToken;
    private Context context;

    public TheTVDBServer(Context context){
        this.context = context;
    }

    @Override
    public void findSeries(final String title, final ResponseReceiver<List<SeriesData>> responseReceiver) {
        DownloadTask<List<SeriesData>> task = new DownloadTask<List<SeriesData>>() {

            @Override
            public String doDownload() throws IOException, NetworkHelper.UnexpectedCodeException {

                return downloadUrl(String.format(SEARCH_URL,title),null,ACCEPT,APPLICATION_JSON,AUTHORIZATION,BEARER+JWTToken);

            }

            @Override
            public ResponseReceiver<List<SeriesData>> getResponseReceiver() {
                return new ResponseReceiver<List<SeriesData>>() {
                    @Override
                    public void onResponseReceived(List<SeriesData> response) {

                        responseReceiver.onResponseReceived(response);

                    }
                    @Override
                    public void onErrorReceived(String message) {

                        if (message.equals(BAD_CODE_FROM_SERVER + ": "
                                + HttpsURLConnection.HTTP_NOT_FOUND))
                            responseReceiver.onResponseReceived(new ArrayList<SeriesData>());
                        else
                            responseReceiver.onErrorReceived(message);
                    }
                };
            }

            @Override
            public void processResult(JSONObject json) throws JSONException {

                List<SeriesData> series = new ArrayList<>();
                JSONArray jsonDataArray = json.getJSONArray("data");

                for(int i = 0; i < jsonDataArray.length();i++){

                    JSONObject jsonData = jsonDataArray.getJSONObject(i);
                    SeriesData seriesData = new SeriesData(jsonData.getInt("id"));
                    seriesData.setTitle(jsonData.getString("seriesName"));
                    seriesData.setFirstAired(jsonData.getString("firstAired"));
                    seriesData.setSummary(jsonData.getString("overview"));
                    seriesData.setStatus(jsonData.getString("status"));
                    seriesData.setNetwork(jsonData.getString("network"));
                    series.add(seriesData);

                }
                responseReceiver.onResponseReceived(series);
            }
        };
        executeDownloadTask(task);
    }

    @Override
    public void findSeason(final int id, final int seasonNumber, final ResponseReceiver<SeasonData> responseReceiver) {

        DownloadTask<SeasonData> task = new DownloadTask<SeasonData>() {

            @Override
            public String doDownload() throws IOException, NetworkHelper.UnexpectedCodeException {

                return downloadUrl(String.format(EPISODES_URL,id,seasonNumber),null,ACCEPT,APPLICATION_JSON,AUTHORIZATION,BEARER+JWTToken);

            }

            @Override
            public ResponseReceiver<SeasonData> getResponseReceiver() {

                return responseReceiver;

            }

            @Override
            public void processResult(JSONObject json) throws JSONException {

                JSONArray jsonDataArray = json.getJSONArray("data");
                SeasonData seasonData = new SeasonData(seasonNumber);
                for(int i = 0;i<jsonDataArray.length();i++){
                    JSONObject jsonData = jsonDataArray.getJSONObject(i);
                    seasonData.addEpisode(new EpisodeInfo(jsonData.getInt("airedEpisodeNumber"),jsonData.getString("episodeName")));
                }
                responseReceiver.onResponseReceived(seasonData);

            }
        };
        executeDownloadTask(task);
    }

    @Override
    public void findSeasons(final int id, final ResponseReceiver<Integer> responseReceiver) {

        DownloadTask<Integer> task = new DownloadTask<Integer>() {

            @Override
            public String doDownload() throws IOException, NetworkHelper.UnexpectedCodeException {

                return downloadUrl(String.format(SEASONS_URL,id),null,ACCEPT,APPLICATION_JSON,AUTHORIZATION,BEARER+JWTToken);

            }

            @Override
            public ResponseReceiver<Integer> getResponseReceiver() {

                return responseReceiver;

            }

            @Override
            public void processResult(JSONObject json) throws JSONException {

                int nMin = -1;

                JSONObject jsonData = json.getJSONObject("data");
                JSONArray jsonArraySeasons = jsonData.getJSONArray("airedSeasons");
                for(int j = 0;j<jsonArraySeasons.length();j++){
                    String jsonDataStrings = jsonArraySeasons.getString(j);
                    int num = Integer.parseInt(jsonDataStrings);
                    if(num > nMin) nMin = num;
                }
                responseReceiver.onResponseReceived(nMin);
            }
        };
        executeDownloadTask(task);
    }

    private boolean getToken(){

        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);

        try{
            jsonWriter.beginObject();
            jsonWriter.name("apikey").value(APIKey);
            jsonWriter.name("username").value(username);
            jsonWriter.name("userkey").value(userkey);
            jsonWriter.endObject();
        } catch (IOException e) {
            return false;
        }
        String payload = stringWriter.toString();
        try {
            String result = downloadUrl(LOGIN_URL, payload, CONTENT_TYPE, APPLICATION_JSON, ACCEPT, APPLICATION_JSON);
            JSONObject json = new JSONObject(result);
            JWTToken = json.getString("token");
            return true;
        } catch (IOException e) {
            return false;
        } catch (JSONException e) {
            return false;
        } catch (NetworkHelper.UnexpectedCodeException e) {
            return false;
        }
    }

    private <T> void executeDownloadTask(final DownloadTask<T> task) {

        final ResponseReceiver<T> responseReceiver = task.getResponseReceiver();

        if (!NetworkHelper.isNetworkConnected(context)) {
            responseReceiver.onErrorReceived(NETWORK_NOT_CONNECTED);
            return;
        }
        new AsyncTask<Void, Void, String>() {

            String errorMessage;

            @Override
            protected String doInBackground(Void... nothing) {

                if (JWTToken == null && !getToken()) {
                    errorMessage = TOKEN_ERROR;
                    return null;
                }
                try {
                    try {
                        return task.doDownload();
                    } catch (NetworkHelper.UnexpectedCodeException e) {
                        if (e.getCode() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                            if (!getToken()) {
                                errorMessage = TOKEN_ERROR;
                                return null;
                            }
                            return task.doDownload();
                        }
                        throw e;
                    }
                } catch (IOException e) {
                    errorMessage = NETWORK_ERROR;
                    return null;
                } catch (NetworkHelper.UnexpectedCodeException e) {
                    errorMessage = BAD_CODE_FROM_SERVER + ": " + e.getCode();
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {

                if (result == null) {
                    responseReceiver.onErrorReceived(errorMessage);
                    return;
                }
                try {
                    JSONObject json = new JSONObject(result);
                    task.processResult(json);
                } catch (JSONException e) {
                    responseReceiver.onErrorReceived(BAD_JSON_IN_SERVER_RESPONSE);
                }

            }
        }.execute();
    }

    private interface DownloadTask<T> {

        String doDownload() throws IOException, NetworkHelper.UnexpectedCodeException;
        ResponseReceiver<T> getResponseReceiver();
        void processResult(JSONObject json) throws JSONException;

    }

}
