package com.hawolt.event.objects.presence;

import com.hawolt.event.BaseObject;
import org.json.JSONObject;

/**
 * Created: 13/04/2022 12:34
 * Author: Twitter @hawolt
 **/

public abstract class AbstractPresence extends BaseObject {

    private final String from, to;
    private final JSONObject raw;
    private String fromRC, toRC;

    public AbstractPresence(JSONObject o) {
        this.raw = o;
        String[] to = o.getString("to").split("/");
        this.to = to[0];
        if (to.length > 1) this.toRC = to[1];
        String[] from = o.getString("from").split("/");
        this.from = from[0];
        if (from.length > 1) this.fromRC = from[1];
    }

    public JSONObject getRaw() {
        return raw;
    }

    public String getFrom() {
        return from;
    }

    public String getFromRC() {
        return fromRC;
    }

    public String getTo() {
        return to;
    }

    public String getToRC() {
        return toRC;
    }

    @Override
    public String toString() {
        return "AbstractPresence{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", fromRC='" + fromRC + '\'' +
                ", toRC='" + toRC + '\'' +
                '}';
    }
}
