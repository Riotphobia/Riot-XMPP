package com.hawolt.event.objects.friends.impl;

import org.json.JSONObject;

/**
 * Created: 14/04/2022 07:37
 * Author: Twitter @hawolt
 **/

public class OfflineFriend extends OnlineFriend {

    private final String lastOnline;
    private final boolean isMobile;

    public OfflineFriend(JSONObject o) {
        super(o);
        this.lastOnline = o.getString("last_online");
        Object state = o.get("state");
        isMobile = (state instanceof JSONObject);
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public boolean isMobile() {
        return isMobile;
    }

    @Override
    public String toString() {
        return "OfflineFriend{" +
                "jid='" + jid + '\'' +
                ", puuid='" + puuid + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", tagline=" + tagline +
                ", lastOnline='" + lastOnline + '\'' +
                ", isMobile=" + isMobile +
                '}';
    }
}
