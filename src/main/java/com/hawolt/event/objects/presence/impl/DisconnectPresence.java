package com.hawolt.event.objects.presence.impl;

import com.hawolt.event.objects.presence.AbstractPresence;
import org.json.JSONObject;

/**
 * Created: 13/04/2022 14:18
 * Author: Twitter @hawolt
 **/

public class DisconnectPresence extends AbstractPresence {
    private final String type, id;

    public DisconnectPresence(JSONObject o) {
        super(o);
        this.type = o.getString("type");
        this.id = o.getString("id");
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
