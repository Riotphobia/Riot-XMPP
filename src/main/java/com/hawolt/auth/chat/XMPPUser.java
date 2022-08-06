package com.hawolt.auth.chat;

import com.hawolt.auth.login.LoginValue;
import com.hawolt.auth.login.RiotUserData;
import com.hawolt.logger.Logger;
import com.hawolt.misc.HttpClient;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Created: 10/04/2022 15:13
 * Author: Twitter @hawolt
 **/

public class XMPPUser {

    private final RiotUserData user;

    public XMPPUser(RiotUserData user) {
        this.user = user;
    }

    public CompletableFuture<String> authorize() {
        CompletableFuture<String> future = new CompletableFuture<>();
        Logger.debug("Attempting to establish session");
        Request request = new Request.Builder()
                .url("https://riot-geo.pas.si.riotgames.com/pas/v1/service/chat")
                .addHeader("Authorization", String.format("Bearer %s", user.get(LoginValue.ACCESS_TOKEN)))
                .get()
                .build();
        Call call = HttpClient.perform(request);
        try (Response response = call.execute()) {
            try (ResponseBody body = response.body()) {
                if (body == null) {
                    future.completeExceptionally(new IOException("Unable to read response body"));
                } else {
                    String content = body.string();
                    Logger.debug(content);
                    future.complete(content);
                }
            }
        } catch (IOException e) {
            future.completeExceptionally(e);
        }
        return future;
    }
}
