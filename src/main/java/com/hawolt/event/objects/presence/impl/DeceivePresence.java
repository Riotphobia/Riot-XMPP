package com.hawolt.event.objects.presence.impl;

import com.hawolt.event.objects.presence.AbstractPresence;
import org.json.JSONObject;

/**
 * Created: 13/04/2022 14:18
 * Author: Twitter @hawolt
 **/

public class DeceivePresence extends AbstractPresence {
    private final String show, id;

    public DeceivePresence(JSONObject o) {
        super(o);
        this.id = o.getString("id");
        this.show = o.getString("show");
    }

    public String getShow() {
        return show;
    }

    public String getId() {
        return id;
    }
}
