package com.devices1.com.myseries.model.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkHelper {
    private static String TAG = NetworkHelper.class.getSimpleName();

    public static class UnexpectedCodeException extends Exception {
        private int code;

        public UnexpectedCodeException(int code) {
            super("Unexpected code: " + code);
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public static boolean isNetworkConnected(Context context) {
        
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    static String downloadUrl(String myurl, String payload, String ... header) throws IOException, UnexpectedCodeException {
        InputStream is = null;

        try {
            Log.i(TAG, "Trying to access to " + myurl);
            URL url = new URL(myurl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod(payload == null ? "GET" : "POST");
            if (header != null) {
                for (int i = 0 ; i < header.length ; i+= 2)
                    conn.setRequestProperty(header[i], header[i+1]);
            }
            conn.setDoInput(true);
            conn.setDoOutput(payload != null);
            // Starts the query
            conn.connect();
            if (payload != null) {
                Log.i(TAG, "Writing payload: " + payload);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(payload);
                writer.close();
            }
            int responseCode = conn.getResponseCode();
            Log.i(TAG, "Response code: " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                is = conn.getInputStream();

                Scanner s = new Scanner(is).useDelimiter("\\A");
                return s.hasNext() ? s.next() : "";
            } else
                throw new UnexpectedCodeException(responseCode);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
