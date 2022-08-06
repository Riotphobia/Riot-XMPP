package com.hawolt.event.events;

import com.hawolt.event.AbstractEvent;
import com.hawolt.event.GenericEvent;
import com.hawolt.event.objects.presence.AbstractPresence;
import com.hawolt.event.objects.presence.Presence;
import com.hawolt.logger.Logger;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created: 11/04/2022 00:11
 * Author: Twitter @hawolt
 **/

public class PresenceEvent extends GenericEvent<AbstractPresence> {

    private AbstractPresence presence;

    public PresenceEvent(AbstractEvent event) {
        super(event);
        try {
            JSONObject object = XML.toJSONObject(event.getPlain()).getJSONObject("presence");
            this.presence = Presence.create(object);
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @Override
    public AbstractPresence get() {
        return presence;
    }
}
