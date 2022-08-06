package com.hawolt.misc;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created: 10/04/2022 15:38
 * Author: Twitter @hawolt
 **/

public class HttpClient {

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";

    private static final OkHttpClient NO_PROXY = new OkHttpClient();

    private static OkHttpClient getClient() {
        return NO_PROXY;
    }

    public static Call get(String uri) {
        OkHttpClient client = getClient();
        Request request = new Request.Builder()
                .addHeader("user-agent", USER_AGENT)
                .url(uri)
                .build();
        return client.newCall(request);
    }

    public static Call perform(Request request) {
        return getClient().newCall(request);
    }

    public static void kill() {
        NO_PROXY.connectionPool().evictAll();
    }

}
