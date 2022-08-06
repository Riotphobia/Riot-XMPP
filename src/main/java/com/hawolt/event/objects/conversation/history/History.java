package com.hawolt.event.objects.conversation.history;

import com.hawolt.event.objects.conversation.history.impl.FailedMessage;
import com.hawolt.event.objects.conversation.history.impl.IncomingMessage;
import com.hawolt.event.objects.conversation.history.impl.OutgoingMessage;
import org.json.JSONObject;

/**
 * Created: 11/04/2022 22:06
 * Author: Twitter @hawolt
 **/

public class History {

    public static AbstractMessage create(JSONObject object) {
        if (object.has("sent")) {
            return new OutgoingMessage(object.getJSONObject("sent").getJSONObject("forwarded").getJSONObject("message"));
        } else if (object.has("error")) {
            return new FailedMessage(object);
        } else {
            if (object.has("stamp")) {
                return new IncomingMessage(object);
            } else {
                return new OutgoingMessage(object);
            }
        }
    }
}
