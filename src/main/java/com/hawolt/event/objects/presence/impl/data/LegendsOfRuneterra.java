package com.hawolt.event.objects.presence.impl.data;

import com.hawolt.event.objects.presence.impl.PresenceData;
import org.json.JSONObject;

import java.util.Base64;

/**
 * Created: 13/04/2022 15:00
 * Author: Twitter @hawolt
 **/

public class LegendsOfRuneterra extends PresenceData {

    private final String s_l, s_r, s_a, sj, s_d, s_c, pg;
    private final JSONObject p;

    public LegendsOfRuneterra(JSONObject o) {
        super(o);
        this.p = new JSONObject(new String(Base64.getDecoder().decode(o.getString("p"))));
        this.s_l = o.getString("s.l");
        this.s_r = o.getString("s.r");
        this.s_a = o.getString("s.a");
        this.sj = o.getString("sj");
        this.s_d = o.getString("s.d");
        this.s_c = o.getString("s.c");
        this.pg = o.getString("s.l");
    }

    @Override
    public JSONObject getDetails() {
        return p;
    }

    public String getSL() {
        return s_l;
    }

    public String getSR() {
        return s_r;
    }

    public String getSA() {
        return s_a;
    }

    public String getSJ() {
        return sj;
    }

    public String getSD() {
        return s_d;
    }

    public String getSC() {
        return s_c;
    }

    public String getPG() {
        return pg;
    }
}
