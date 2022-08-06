package com.hawolt.auth.login.entitlement;

import com.hawolt.auth.login.LoginValue;
import com.hawolt.auth.login.RiotUserData;
import com.hawolt.misc.HttpClient;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Created: 10/04/2022 18:34
 * Author: Twitter @hawolt
 **/

public class EntitlementUser {

    private static final RequestBody REQUEST_BODY = RequestBody.create(MediaType.parse("application/json"), "");

    private final RiotUserData user;

    public EntitlementUser(RiotUserData user) {
        this.user = user;
    }

    public CompletableFuture<String> authorize() {
        CompletableFuture<String> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("https://entitlements.auth.riotgames.com/api/token/v1")
                .addHeader("Authorization", String.format("Bearer %s", user.get(LoginValue.ACCESS_TOKEN)))
                .addHeader("Content-Type", "application/json")
                .post(REQUEST_BODY)
                .build();
        Call call = HttpClient.perform(request);
        try (Response response = call.execute()) {
            try (ResponseBody body = response.body()) {
                if (body == null) {
                    future.completeExceptionally(new IOException("Unable to read response body"));
                } else {
                    JSONObject object = new JSONObject(body.string());
                    future.complete(object.getString("entitlements_token"));
                }
            }
        } catch (IOException e) {
            future.completeExceptionally(e);
        }
        return future;
    }
}
