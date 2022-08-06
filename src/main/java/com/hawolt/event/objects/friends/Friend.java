package com.hawolt.event.objects.friends;

import com.hawolt.event.objects.friends.impl.OfflineFriend;
import com.hawolt.event.objects.friends.impl.OnlineFriend;
import org.json.JSONObject;

/**
 * Created: 11/04/2022 12:44
 * Author: Twitter @hawolt
 **/

public class Friend {

    public static GenericFriend create(JSONObject o) {
        if (!o.has("name")) {
            return new GenericFriend(o);
        } else {
            if (o.has("last_online")) {
                return new OfflineFriend(o);
            } else {
                return new OnlineFriend(o);
            }
        }
    }
}
