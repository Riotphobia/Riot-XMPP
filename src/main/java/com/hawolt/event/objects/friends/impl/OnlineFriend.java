package com.hawolt.event.objects.friends.impl;

import com.hawolt.event.objects.friends.GenericFriend;
import org.json.JSONObject;

/**
 * Created: 14/04/2022 07:30
 * Author: Twitter @hawolt
 **/

public class OnlineFriend extends GenericFriend {

    private final String lolName;

    public OnlineFriend(JSONObject o) {
        super(o);
        this.lolName = o.get("name").toString();
    }

    public String getLOLName() {
        return lolName;
    }

    @Override
    public String toString() {
        return "OnlineFriend{" +
                "jid='" + jid + '\'' +
                ", puuid='" + puuid + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", tagline=" + tagline +
                ", lolName='" + lolName + '\'' +
                '}';
    }
}
