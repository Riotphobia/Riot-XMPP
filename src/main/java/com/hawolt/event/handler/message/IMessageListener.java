package com.hawolt.event.handler.message;

import com.hawolt.event.objects.conversation.history.impl.FailedMessage;
import com.hawolt.event.objects.conversation.history.impl.IncomingMessage;
import com.hawolt.event.objects.conversation.history.impl.OutgoingMessage;

/**
 * Created: 18/04/2022 01:18
 * Author: Twitter @hawolt
 **/

public interface IMessageListener {
    void onMessageReceived(IncomingMessage message);

    void onMessageSent(OutgoingMessage message);

    void onFailedMessage(FailedMessage message);
}
