package com.hawolt.event.objects.presence.impl;

import com.hawolt.event.objects.presence.AbstractPresence;
import org.json.JSONObject;

/**
 * Created: 13/04/2022 14:18
 * Author: Twitter @hawolt
 **/

public class OnlinePresence extends AbstractPresence {
    private final String show, id;
    private String status;

    public OnlinePresence(JSONObject o) {
        super(o);
        this.id = o.getString("id");
        this.show = o.getString("show");
        if (o.has("status")) this.status = o.get("status").toString();
    }

    public String getShow() {
        return show;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "OnlinePresence{" +
                "show='" + show + '\'' +
                ", id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
