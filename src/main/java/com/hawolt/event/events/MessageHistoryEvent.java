package com.hawolt.event.events;

import com.hawolt.logger.Logger;
import com.hawolt.event.AbstractEvent;
import com.hawolt.event.GenericEvent;
import com.hawolt.event.objects.conversation.history.History;
import com.hawolt.event.objects.conversation.history.MessageHistory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created: 11/04/2022 00:11
 * Author: Twitter @hawolt
 **/

public class MessageHistoryEvent extends GenericEvent<MessageHistory> {

    private final MessageHistory history = new MessageHistory();

    public MessageHistoryEvent(AbstractEvent event) {
        super(event);
        try {
            JSONObject iq = XML.toJSONObject(event.getPlain()).getJSONObject("iq");
            history.setId(iq.getString("id"));
            if (!iq.has("message")) return;
            if (iq.get("message") instanceof JSONArray) {
                JSONArray message = iq.getJSONArray("message");
                for (int i = 0; i < message.length(); i++) {
                    history.add(History.create(message.getJSONObject(i)));
                }
            } else {
                history.add(History.create(iq.getJSONObject("message")));
            }
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @Override
    public MessageHistory get() {
        return history;
    }

}
