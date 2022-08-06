package com.hawolt.event.objects.presence.impl.data;

import com.hawolt.event.objects.presence.impl.PresenceData;
import org.json.JSONObject;

/**
 * Created: 13/04/2022 15:00
 * Author: Twitter @hawolt
 **/

public class LeagueOfLegends extends PresenceData {
    private final JSONObject p;
    private final String s_r, s_c;

    public LeagueOfLegends(JSONObject o) {
        super(o);
        this.p = o.getJSONObject("p");
        this.s_r = o.getString("s.r");
        this.s_c = o.getString("s.c");
    }


    @Override
    public JSONObject getDetails() {
        return p;
    }

    public String getSR() {
        return s_r;
    }

    public String getSC() {
        return s_c;
    }
}
