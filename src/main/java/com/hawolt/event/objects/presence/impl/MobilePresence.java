package com.hawolt.event.objects.presence.impl;

import com.hawolt.event.objects.presence.AbstractPresence;
import org.json.JSONObject;

/**
 * Created: 13/04/2022 14:18
 * Author: Twitter @hawolt
 **/

public class MobilePresence extends AbstractPresence {
    private final String type, lastOnline;
    private final Object show;

    public MobilePresence(JSONObject o) {
        super(o);
        this.type = o.getString("type");
        this.show = o.getString("show");
        this.lastOnline = o.getString("last_online");
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public Object getShow() {
        return show;
    }

    public String getType() {
        return type;
    }
}
