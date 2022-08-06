package com.hawolt.event.objects.presence.impl;

import org.json.JSONObject;

/**
 * Created: 13/04/2022 14:55
 * Author: Twitter @hawolt
 **/

public abstract class PresenceData {
    private final Object st, m;
    private final String s_p;
    private final long s_t;

    public PresenceData(JSONObject o) {
        this.st = o.get("st");
        this.s_p = o.getString("s.p");
        this.s_t = o.getLong("s.t");
        this.m = o.get("m");
    }

    public Object getST() {
        return st;
    }

    public String getS_P() {
        return s_p;
    }

    public long getS_T() {
        return s_t;
    }

    public Object getM() {
        return m;
    }

    public abstract JSONObject getDetails();
}
