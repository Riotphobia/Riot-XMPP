package com.hawolt.auth.riot;

import com.hawolt.misc.HttpClient;
import com.hawolt.misc.StaticConfig;
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
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody post = RequestBody.create(StaticConfig.RIOT_POST, mediaType);
        Request request = new Request.Builder()
                .url("https://auth.riotgames.com/api/v1/authorization/")
                .addHeader("Content-Type", "application/json")
                .post(post)
                .build();
        Call call = HttpClient.perform(request);
        try (Response response = call.execute()) {
            if (response.code() == 200) {
                return build(response.headers("set-cookie"));
            }
        }
        throw new IOException("No Cookie given");
    }
}
