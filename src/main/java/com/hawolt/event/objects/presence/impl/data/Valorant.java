package com.hawolt.event.objects.presence.impl.data;

import com.hawolt.event.objects.presence.impl.PresenceData;
import org.json.JSONObject;

import java.util.Base64;

/**
 * Created: 13/04/2022 15:00
 * Author: Twitter @hawolt
 **/

public class Valorant extends PresenceData {
    private final String s_l, s_a, s_d;
    private final JSONObject p;

    public Valorant(JSONObject o) {
        super(o);
        this.p = new JSONObject(new String(Base64.getDecoder().decode(o.getString("p"))));
        this.s_l = o.getString("s.l");
        this.s_a = o.getString("s.a");
        this.s_d = o.getString("s.d");
    }

    @Override
    public JSONObject getDetails() {
        return p;
    }

    public String getSL() {
        return s_l;
    }

    public String getSA() {
        return s_a;
    }

    public String getSD() {
        return s_d;
    }

    public JSONObject getP() {
        return p;
    }
}
