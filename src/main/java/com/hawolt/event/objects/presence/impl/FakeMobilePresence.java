package com.hawolt.event.objects.presence.impl;

import com.hawolt.event.objects.presence.AbstractPresence;
import org.json.JSONObject;

/**
 * Created: 13/04/2022 14:18
 * Author: Twitter @hawolt
 **/

public class FakeMobilePresence extends AbstractPresence {
    private final String show;

    public FakeMobilePresence(JSONObject o) {
        super(o);
        this.show = o.getString("show");
    }

    public String getShow() {
        return show;
    }
}
