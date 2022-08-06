package com.hawolt.misc;

import com.hawolt.auth.RiotChatServer;
import org.json.JSONObject;

/**
 * Created: 10/04/2022 15:38
 * Author: Twitter @hawolt
 **/

public class StaticConfig {

    public static final String HEARTBEAT = " ";

    public static String SOCKET_CONNECT(RiotChatServer server) {
        return String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><stream:stream to=\"%s.pvp.net\" xml:lang=\"en\" version=\"1.0\" xmlns=\"jabber:client\" xmlns:stream=\"http://etherx.jabber.org/streams\">", server.getDomain());
    }

    public static final String RIOT_POST = "{\n" +
            "   \"acr_values\":\"urn:riot:bronze\",\n" +
            "   \"claims\":\"\",\n" +
            "   \"client_id\":\"lol\",\n" +
            "   \"nonce\":\"oYnVwCSrlS5IHKh7iI17oQ\",\n" +
            "   \"redirect_uri\":\"http://localhost/redirect\",\n" +
            "   \"response_type\":\"token id_token\",\n" +
            "   \"scope\":\"openid link ban lol_region\"\n" +
            "}";

    public static String RIOT_PUT(String username, String password) {
        JSONObject object = new JSONObject();
        object.put("type", "auth");
        object.put("username", username);
        object.put("password", password);
        object.put("remember", false);
        object.put("language", "en_GB");
        object.put("region", JSONObject.NULL);
        return object.toString();
    }
}
