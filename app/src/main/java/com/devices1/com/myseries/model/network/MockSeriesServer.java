package com.devices1.com.myseries.model.network;

import android.os.AsyncTask;

import com.devices1.com.myseries.model.EpisodeInfo;
import com.devices1.com.myseries.model.SeasonData;
import com.devices1.com.myseries.model.SeriesData;
import com.devices1.com.myseries.model.database.SeriesAndSeasons;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*
    A mock server that only contains a set of fixed series.
 */

public class MockSeriesServer implements ISeriesServer {
    private static final List<SeriesAndSeasons> allSeries;

    static {
        SeriesAndSeasons seriesAndSeasons;
        allSeries = new ArrayList<>();
        seriesAndSeasons = addSeries(81189,
            "Breaking Bad",
            "Walter White, a struggling high school chemistry teacher, is diagnosed with advanced lung cancer. He turns to a life of crime, producing and selling methamphetamine accompanied by a former student, Jesse Pinkman, with the aim of securing his family's financial future before he dies.",
            "2008-01-20",
            "AMC",
            "Ended",
            5);

        addSeason(seriesAndSeasons, 1, "First", "Second", "Third");
        addSeason(seriesAndSeasons, 2, "First", "Second", "Third", "Fourth");
        addSeason(seriesAndSeasons, 3, "First", "Second", "Third", "Fourth");
        addSeason(seriesAndSeasons, 4, "First", "Second", "Third");
        addSeason(seriesAndSeasons, 5, "First", "Second", "Third", "Fourth", "Fifth");

        seriesAndSeasons = addSeries(176941,
            "Sherlock",
            "Sherlock is a British television crime drama that presents a contemporary adaptation of Sir Arthur Conan Doyle's Sherlock Holmes detective stories. Created by Steven Moffat and Mark Gatiss, it stars Benedict Cumberbatch as Sherlock Holmes and Martin Freeman as Doctor John Watson.",
            "2010-07-25",
            "BBC One",
            "Continuing",
            4);

        addSeason(seriesAndSeasons, 1, "First", "Second", "Third");
        addSeason(seriesAndSeasons, 2, "First", "Second", "Third", "Fourth");
        addSeason(seriesAndSeasons, 3, "First", "Second", "Third", "Fourth");
        addSeason(seriesAndSeasons, 4, "First", "Second", "Third", "Fourth");

        seriesAndSeasons = addSeries(296762,
            "Westworld",
            "Westworld is a dark odyssey about the dawn of artificial consciousness and the evolution of sin. Set at the intersection of the near future and the reimagined past, it explores a world in which every human appetite, no matter how noble or depraved, can be indulged.",
            "2016-10-02",
            "HBO",
            "Continuning",
            1);

        addSeason(seriesAndSeasons, 1, "First", "Second", "Third");
    }

    private static void addSeason(SeriesAndSeasons seriesAndSeasons, int seasonNumber, String ... titles) {
        SeasonData seasonData = seriesAndSeasons.createSeason(seasonNumber);
        int number = 1;
        //for (String title : titles)
        //    seasonData.addEpisode(new EpisodeInfo(number++, title));
    }

    private static SeriesAndSeasons addSeries(int id, String title, String summary, String firstAired,
                           String network, String status, int seasons) {
        SeriesData data = new SeriesData(id);
        data.setTitle(title);
        data.setSummary(summary);
        data.setFirstAired(firstAired);
        data.setNetwork(network);
        data.setStatus(status);
        data.setNumberOfSeasons(seasons);
        SeriesAndSeasons seriesAndSeasons = new SeriesAndSeasons(data);
        allSeries.add(seriesAndSeasons);
        return seriesAndSeasons;
    }

    private static class DelayedFindSeries extends AsyncTask<String, Void, List<SeriesData>> {
        final ResponseReceiver<List<SeriesData>> responseReceiver;

        public DelayedFindSeries(ResponseReceiver<List<SeriesData>> responseReceiver) {
            this.responseReceiver = responseReceiver;
        }

        @Override
        protected List<SeriesData> doInBackground(String... strings) {
            String title = strings[0];
            List<SeriesData> result = new ArrayList<>();
            for (SeriesAndSeasons seriesAndSeasons: allSeries)
                if (seriesAndSeasons.getTitle().contains(title)) {
                    SeriesData seriesData = seriesAndSeasons.getSeriesData().copy();
                    seriesData.setNumberOfSeasons(0);
                    result.add(seriesData);
                }
            return result;
        }

        @Override
        protected void onPostExecute(List<SeriesData> seriesData) {
            responseReceiver.onResponseReceived(seriesData);
        }
    }

    @Override
    public void findSeries(final String title, final ResponseReceiver<List<SeriesData>> responseReceiver) {
        if (title.equals("error")) {
            responseReceiver.onErrorReceived("Error while searching");
            return;
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DelayedFindSeries delayedFindSeries = new DelayedFindSeries(responseReceiver);
                delayedFindSeries.execute(title);
            }
        }, 1000);
    }

    @Override
    public void findSeason(int id, int seasonNumber, ResponseReceiver<SeasonData> responseReceiver) {
        for (SeriesAndSeasons seriesAndSeasons: allSeries)
            if (seriesAndSeasons.getId() == id) {
                SeasonData seasonData = seriesAndSeasons.getSeason(seasonNumber);
                if (seasonData == null)
                    responseReceiver.onErrorReceived("Season not found");
                else
                    responseReceiver.onResponseReceived(seasonData);
                return;
            }
        responseReceiver.onErrorReceived("Series not found");
    }

    @Override
    public void findSeasons(int id, ResponseReceiver<Integer> responseReceiver) {
        for (SeriesAndSeasons seriesAndSeasons: allSeries)
            if (seriesAndSeasons.getId() == id) {
                responseReceiver.onResponseReceived(seriesAndSeasons.getSeriesData().getNumberOfSeasons());
                return;
            }
        responseReceiver.onErrorReceived("Series not found");
    }
}
