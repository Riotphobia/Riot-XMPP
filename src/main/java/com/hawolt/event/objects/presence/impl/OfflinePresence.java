package com.hawolt.event.objects.presence.impl;

import com.hawolt.event.objects.presence.AbstractPresence;
import org.json.JSONObject;

/**
 * Created: 13/04/2022 14:18
 * Author: Twitter @hawolt
 **/

public class OfflinePresence extends AbstractPresence {
    private final String type;

    public OfflinePresence(JSONObject o) {
        super(o);
        this.type = o.getString("type");
    }

    public String getType() {
        return type;
    }
}
