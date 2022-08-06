package com.hawolt.event;

import com.hawolt.event.events.*;
import com.hawolt.event.handler.socket.ISocketListener;
import com.hawolt.logger.Logger;
import com.hawolt.misc.Unsafe;
import com.hawolt.xmpp.IdentityCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created: 11/04/2022 00:11
 * Author: Twitter @hawolt
 **/

public abstract class AbstractEventHandler implements ISocketListener, IdentityCallback, IEvent {

    protected final Map<EventType, Function<AbstractEvent, GenericEvent<?>>> events = new HashMap<>();
    private final Map<EventType, List<EventListener<?>>> handlers = new HashMap<>();

    public AbstractEventHandler() {
        events.put(EventType.RECENT_CONVERSATIONS, RecentConversationEvent::new);
        events.put(EventType.FRIEND_REQUEST_STATUS, FriendStatusEvent::new);
        events.put(EventType.MESSAGE_HISTORY, MessageHistoryEvent::new);
        events.put(EventType.SESSION_EXPIRED, SessionExpiredEvent::new);
        events.put(EventType.FRIEND_REQUEST, FriendRequestEvent::new);
        events.put(EventType.PERSONAL_INFO, PersonalInfoEvent::new);
        events.put(EventType.SUMMONER_NAME, SummonerNameEvent::new);
        events.put(EventType.FRIEND_LIST, FriendListEvent::new);
        events.put(EventType.PRESENCE, PresenceEvent::new);
        events.put(EventType.FAILURE, FailureEvent::new);
        events.put(EventType.MESSAGE, MessageEvent::new);
        events.put(EventType.UNKNOWN, UnknownEvent::new);
        events.put(EventType.ON_READY, ReadyEvent::new);
    }

    public void addHandler(EventType type, EventListener<?> listener) {
        if (!handlers.containsKey(type)) handlers.put(type, new ArrayList<>());
        handlers.get(type).add(listener);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        GenericEvent<?> generic = events.get(event.getType()).apply(event);
        if (!handlers.containsKey(generic.getType())) return;
        String identifier = getIdentity() == null ? getConnectionIdentifier() : String.join(":", getConnectionIdentifier(), getIdentity().getPUUID());
        handlers.get(generic.getType()).forEach(o -> {
            try {
                o.onEvent(Unsafe.cast(generic.get()));
            } catch (Exception e) {
                Logger.error("{} {}", identifier, e.getMessage() == null ? "NO_EXCEPTION_MESSAGE" : e.getMessage());
                Logger.error(e);
                if (e.getMessage().equals("NO_IDENTITY_AVAILABLE")) {
                    onUnavailableIdentity();
                } else {
                    onException(e);
                }
            }
        });
    }

    protected abstract void onException(Exception e);

    protected abstract void onUnavailableIdentity();
}
