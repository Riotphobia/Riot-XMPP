package com.hawolt.event.events;

import com.hawolt.event.AbstractEvent;
import com.hawolt.event.GenericEvent;
import com.hawolt.event.objects.friends.Friend;
import com.hawolt.event.objects.friends.GenericFriend;
import com.hawolt.logger.Logger;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created: 11/04/2022 00:11
 * Author: Twitter @hawolt
 **/

public class FriendRequestEvent extends GenericEvent<GenericFriend> {

    private GenericFriend friend;

    public FriendRequestEvent(AbstractEvent event) {
        super(event);
        try {
            JSONObject object = XML.toJSONObject(event.getPlain()).getJSONObject("iq").getJSONObject("query").getJSONObject("item");
            this.friend = Friend.create(object);
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @Override
    public GenericFriend get() {
        return friend;
    }
}
