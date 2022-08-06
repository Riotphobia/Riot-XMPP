package com.hawolt.event.objects.presence.impl.data;

import com.hawolt.event.objects.presence.impl.PresenceData;
import org.json.JSONObject;

/**
 * Created: 13/04/2022 15:00
 * Author: Twitter @hawolt
 **/

public class Keystone extends PresenceData {
    public Keystone(JSONObject o) {
        super(o);
    }

    @Override
    public JSONObject getDetails() {
        return null;
    }
}
