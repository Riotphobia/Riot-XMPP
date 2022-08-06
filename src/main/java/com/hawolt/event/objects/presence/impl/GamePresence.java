package com.hawolt.event.objects.presence.impl;

import com.hawolt.event.objects.presence.impl.data.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: 13/04/2022 12:43
 * Author: Twitter @hawolt
 **/

public class GamePresence extends OnlinePresence {

    private final List<PresenceData> list = new ArrayList<>();

    public GamePresence(JSONObject object) {
        super(object);
        JSONObject games = object.getJSONObject("games");
        for (String o : games.keySet()) {
            JSONObject data = games.getJSONObject(o);
            if ("keystone".equals(o)) {
                list.add(new Keystone(data));
            } else if ("league_of_legends".equals(o)) {
                list.add(new LeagueOfLegends(data));
            } else if ("valorant".equals(o)) {
                list.add(new Valorant(data));
            } else if ("bacon".equals(o)) {
                list.add(new LegendsOfRuneterra(data));
            } else if ("wildrift".equals(o)) {
                list.add(new WildRift(data));
            }
        }
    }

    public List<PresenceData> getPresenceData() {
        return list;
    }
}
