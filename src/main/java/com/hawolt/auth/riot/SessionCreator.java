package com.hawolt.auth.riot;

import com.hawolt.logger.Logger;
import com.hawolt.misc.HttpClient;
import com.hawolt.misc.StaticConfig;
import kotlin.Pair;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class SessionCreator {

    private static String build(List<String> list) {
        if (list == null || list.isEmpty()) return null;
        StringBuilder builder = new StringBuilder();
        for (String string : list) {
            String[] data = string.split(";");
            builder.append(data[0]).append("; ");
        }
        return builder.toString().trim();
    }

    public static String getCookie() throws IOException {
        return getCookie(getCookie(null));
    }

    private static String getCookie(String __cf_bm) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody post = RequestBody.create(StaticConfig.RIOT_POST, mediaType);
        Request.Builder builder = new Request.Builder()
                .url("https://auth.riotgames.com/api/v1/authorization")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "RiotClient/18.0.0 (lol-store)")
                .addHeader("Pragma", "no-cache")
                .post(post);
        if (__cf_bm != null) builder.addHeader("Cookie", __cf_bm);
        Request request = builder.build();
        Call call = HttpClient.perform(request);
        try (Response response = call.execute()) {
            if (__cf_bm == null) return build(response.headers("set-cookie"));
            if (response.code() == 200) {
                return build(response.headers("set-cookie"));
            }
        }
        throw new IOException("No Cookie given");
    }
}
