package com.hawolt.event.events;

import com.hawolt.logger.Logger;
import com.hawolt.event.AbstractEvent;
import com.hawolt.event.GenericEvent;
import com.hawolt.event.objects.friends.status.FriendStatus;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created: 11/04/2022 00:11
 * Author: Twitter @hawolt
 **/

public class FriendStatusEvent extends GenericEvent<FriendStatus> {

    private FriendStatus status;

    public FriendStatusEvent(AbstractEvent event) {
        super(event);
        try {
            JSONObject object = XML.toJSONObject(event.getPlain()).getJSONObject("iq");
            this.status = new FriendStatus(object);
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @Override
    public FriendStatus get() {
        return status;
    }
}
