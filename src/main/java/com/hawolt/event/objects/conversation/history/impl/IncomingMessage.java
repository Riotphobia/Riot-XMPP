package com.hawolt.event.objects.conversation.history.impl;

import com.hawolt.event.objects.conversation.hint.AbstractConversation;
import com.hawolt.event.objects.conversation.history.AbstractMessage;
import org.json.JSONObject;

/**
 * Created: 11/04/2022 22:16
 * Author: Twitter @hawolt
 **/

public class IncomingMessage extends AbstractMessage {
    private String rc, stamp;

    public IncomingMessage(JSONObject object) {
        super(object);
        this.stamp = object.getString("stamp");
        String[] from = object.getString("from").split("/");
        if (from.length > 1) this.rc = from[1];
        this.from = from[0];
    }

    private IncomingMessage(AbstractConversation conversation) {
        super(conversation);
    }

    public String getStamp() {
        return stamp;
    }

    public String getFrom() {
        return from;
    }

    public String getRC() {
        return rc;
    }

    public static IncomingMessage construct(AbstractConversation conversation) {
        return new IncomingMessage(conversation);
    }
}
