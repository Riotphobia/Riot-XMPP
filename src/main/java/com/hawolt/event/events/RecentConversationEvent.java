package com.hawolt.event.events;

import com.hawolt.logger.Logger;
import com.hawolt.event.AbstractEvent;
import com.hawolt.event.GenericEvent;
import com.hawolt.event.objects.conversation.hint.Conversation;
import com.hawolt.event.objects.conversation.hint.RecentConversations;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created: 11/04/2022 16:58
 * Author: Twitter @hawolt
 **/

public class RecentConversationEvent extends GenericEvent<RecentConversations> {

    private final RecentConversations conversations = new RecentConversations();

    public RecentConversationEvent(AbstractEvent event) {
        super(event);
        try {
            JSONObject object = XML.toJSONObject(event.getPlain());
            if (!object.has("iq")) return;
            JSONObject iq = object.getJSONObject("iq");
            if (!iq.has("query")) return;
            JSONObject query = iq.getJSONObject("query");
            if (!query.has("item")) return;
            if (query.get("item") instanceof JSONArray) {
                JSONArray item = query.getJSONArray("item");
                for (int i = 0; i < item.length(); i++) {
                    JSONObject data = item.getJSONObject(i);
                    conversations.add(Conversation.create(data));
                }
            } else {
                conversations.add(Conversation.create(query.getJSONObject("item")));
            }
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @Override
    public RecentConversations get() {
        return conversations;
    }
}
