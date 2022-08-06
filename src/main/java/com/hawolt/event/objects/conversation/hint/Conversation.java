package com.hawolt.event.objects.conversation.hint;

import com.hawolt.event.objects.conversation.hint.impl.ReadConversation;
import com.hawolt.event.objects.conversation.hint.impl.UnreadConversation;
import org.json.JSONObject;

/**
 * Created: 11/04/2022 18:10
 * Author: Twitter @hawolt
 **/

public class Conversation {

    public static AbstractConversation create(JSONObject object) {
        if (!object.has("reader")) {
            return new UnreadConversation(object);
        } else {
            return new ReadConversation(object);
        }
    }
}
