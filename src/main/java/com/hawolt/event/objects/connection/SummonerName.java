package com.hawolt.event.objects.connection;

import com.hawolt.event.BaseObject;
import org.json.JSONObject;

/**
 * Created: 30/04/2022 20:54
 * Author: Twitter @hawolt
 **/

public class SummonerName extends BaseObject {

    private final String name;

    public SummonerName(JSONObject object) {
        this.name = object.getJSONObject("iq").getJSONObject("session").getString("summoner_name");
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SummonerName{" +
                "name='" + name + '\'' +
                '}';
    }
}
