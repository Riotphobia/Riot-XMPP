package com.hawolt.xmpp;

import com.hawolt.logger.Logger;
import com.hawolt.auth.IRiotDataCallback;
import com.hawolt.auth.RateLimitController;
import com.hawolt.auth.RiotChatServer;
import com.hawolt.auth.RiotUser;
import com.hawolt.auth.chat.XMPPUser;
import com.hawolt.auth.login.LoginValue;
import com.hawolt.auth.login.RiotUserData;
import com.hawolt.event.handler.socket.ISocketListener;
import org.json.JSONObject;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

/**
 * Created: 10/04/2022 15:23
 * Author: Twitter @hawolt
 **/

public class RiotXMPPAccount {
    private final RiotUser user;

    public RiotXMPPAccount(RiotUser user) {
        this.user = user;
    }

    public CompletableFuture<RiotXMPPClient> connect() {
        return connect(null);
    }

    public CompletableFuture<RiotXMPPClient> connect(ISocketListener listener) {
        CompletableFuture<RiotXMPPClient> future = new CompletableFuture<>();
        RateLimitController.add(() -> {
            user.login().whenComplete((data, ex1) -> {
                if (ex1 != null) future.completeExceptionally(ex1);
                else {
                    JSONObject personal = new JSONObject(new String(Base64.getDecoder().decode(data.get(LoginValue.ID_TOKEN).split("\\.")[1])));
                    final String region = personal.has("lol") ? personal.getJSONArray("lol").getJSONObject(0).getString("cpid") : "";
                    XMPPUser xmpp = new XMPPUser(data);
                    xmpp.authorize().whenComplete((token, ex2) -> {
                        Logger.debug("Completed authorization with {}", new String(Base64.getDecoder().decode(token.split("\\.")[1])));
                        JSONObject object = new JSONObject(new String(Base64.getDecoder().decode(token.split("\\.")[1])));
                        final RiotChatServer server = RiotChatServer.valueOf(object.getString("affinity").replace("-", "_").toUpperCase());
                        Logger.debug("SELECTED AFFINITY {} ON GAME REGION {}", server.name(), region);
                        if (ex2 != null) future.completeExceptionally(ex2);
                        else {
                            try {
                                IRiotDataCallback callback = new IRiotDataCallback() {
                                    @Override
                                    public String getGameRegion() {
                                        return region;
                                    }

                                    @Override
                                    public RiotUser getRiotUser() {
                                        return user;
                                    }

                                    @Override
                                    public RiotUserData getRiotUserData() {
                                        return data;
                                    }

                                    @Override
                                    public String getXMPPToken() {
                                        return token;
                                    }

                                    @Override
                                    public RiotChatServer getChatServer() {
                                        return server;
                                    }
                                };
                                Socket socket = SSLSocketFactory.getDefault().createSocket(server.getHost(), server.getPort());
                                socket.setKeepAlive(true);
                                RiotXMPPClient riotXMPPClient = new RiotXMPPClient(callback, socket, listener);
                                future.complete(riotXMPPClient);
                            } catch (IOException e) {
                                future.completeExceptionally(e);
                            }
                        }
                    });
                }
            });
        });
        return future;
    }
}
