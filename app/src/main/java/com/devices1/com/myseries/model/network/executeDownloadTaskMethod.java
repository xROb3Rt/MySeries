package com.devices1.com.myseries.model.network;//
// This is only a fragment of the file TheTVDBServer.java
//
// Include the interface and the method inside the TheTVDBServer class
//
/*
   private static final String TOKEN_ERROR = "I could not get a token from the server";
   private static final String NETWORK_ERROR = "Network error";
   private static final String NETWORK_NOT_CONNECTED = "Network not connected";
   private static final String BAD_CODE_FROM_SERVER = "Bad code from server";
   private static final String BAD_JSON_IN_SERVER_RESPONSE = "Bad JSON in server response";


   private interface DownloadTask<T> {
        String doDownload() throws IOException, UnexpectedCodeException;

        ResponseReceiver<T> getResponseReceiver();

        void processResult(JSONObject json) throws JSONException;
    }

    <T> void executeDownloadTask(final DownloadTask<T> task) {
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
                    } catch (UnexpectedCodeException e) {
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
                } catch (UnexpectedCodeException e) {
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
*/