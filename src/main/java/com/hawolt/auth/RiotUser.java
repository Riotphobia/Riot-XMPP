package com.hawolt.auth;

import com.hawolt.auth.login.RiotUserData;
import com.hawolt.auth.riot.SessionCreator;
import com.hawolt.logger.Logger;
import com.hawolt.misc.HttpClient;
import com.hawolt.misc.StaticConfig;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Created: 10/04/2022 14:48
 * Author: Twitter @hawolt
 **/

public class RiotUser {

    private final String username, password;

    public RiotUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public CompletableFuture<RiotUserData> login() {
        CompletableFuture<RiotUserData> future = new CompletableFuture<>();
        try {
            Logger.debug("Attempting to fetch Cookie");
            String cookie = SessionCreator.getCookie();
            Logger.debug("Cookie {}", cookie);
            if (cookie == null) {
                future.completeExceptionally(new IOException("Unable to request Session Cookie"));
            } else {
                MediaType mediaType = MediaType.parse("application/json");
                String put = StaticConfig.RIOT_PUT(username, password);
                RequestBody login = RequestBody.create(mediaType, put);
                Request request = new Request.Builder()
                        .url("https://auth.riotgames.com/api/v1/authorization")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
                        .addHeader("Pragma", "no-cache")
                        .addHeader("Accept", "*/*")
                        .addHeader("Cookie", cookie)
                        .put(login)
                        .build();
                Call call = HttpClient.perform(request);
                try (Response response = call.execute()) {
                    try (ResponseBody body = response.body()) {
                        if (body == null) {
                            future.completeExceptionally(new IOException("Unable to read response body"));
                        } else {
                            String content = body.string();
                            Logger.debug(content);
                            if (content.contains("invalid_session_id")) {
                                future.completeExceptionally(new IOException("Unknown session ID"));
                            } else if (content.contains("auth_failure") || content.length() == 0) {
                                future.completeExceptionally(new IOException("Bad login"));
                            } else if (content.contains("rate_limited")) {
                                future.completeExceptionally(new IOException("Rate limited"));
                            } else {
                                JSONObject object = new JSONObject(content);
                                if (object.has("response")) {
                                    JSONObject nestOne = object.getJSONObject("response");
                                    if (nestOne.has("parameters")) {
                                        JSONObject nestTwo = nestOne.getJSONObject("parameters");
                                        if (nestTwo.has("uri")) {
                                            String values = nestTwo.getString("uri");
                                            String client = values.split("#")[1];
                                            JSONObject data = new JSONObject();
                                            String[] parameters = client.split("&");
                                            for (String parameter : parameters) {
                                                String[] pair = parameter.split("=");
                                                data.put(pair[0], pair[1]);
                                            }
                                            future.complete(new RiotUserData(data));
                                        }
                                    }
                                } else {
                                    future.completeExceptionally(new IOException("Unable to parse token"));
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            future.completeExceptionally(e);
        }
        return future;
    }

}
