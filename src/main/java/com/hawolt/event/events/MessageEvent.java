package com.hawolt.event.events;

import com.hawolt.event.AbstractEvent;
import com.hawolt.event.GenericEvent;
import com.hawolt.event.objects.conversation.history.AbstractMessage;
import com.hawolt.event.objects.conversation.history.History;
import com.hawolt.logger.Logger;
import org.json.XML;

/**
 * Created: 11/04/2022 00:11
 * Author: Twitter @hawolt
 **/

public class MessageEvent extends GenericEvent<AbstractMessage> {

    private AbstractMessage message;

    public MessageEvent(AbstractEvent event) {
        super(event);
        try {
            boolean isSelf = !event.getPlain().endsWith("</error></message>") && event.getPlain().startsWith("<message from='" + event.getIdentity().getPUUID());
            String plain = isSelf ? event.getPlain() + "</forwarded></sent></message>" : event.getPlain();
            this.message = History.create(XML.toJSONObject(plain).getJSONObject("message"));
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @Override
    public AbstractMessage get() {
        return message;
    }

}
