package com.hawolt.event.objects.friends;

import com.hawolt.logger.Logger;
import com.hawolt.event.BaseObject;
import com.hawolt.event.handler.IBaseDispatcher;
import com.hawolt.event.handler.Observer;
import com.hawolt.event.objects.friends.impl.OnlineFriend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created: 14/04/2022 03:38
 * Author: Twitter @hawolt
 **/

public class FriendList extends BaseObject implements Observer<IFriendListener>, IBaseDispatcher<FriendEventType, GenericFriend> {

    private final Map<SubscriptionType, List<GenericFriend>> map = new HashMap<>();
    private final Map<String, String> cache = new HashMap<>();

    private final List<IFriendListener> observers = new ArrayList<>();

    public FriendList(GenericFriend... friends) {
        for (GenericFriend friend : friends) {
            put(friend);
        }
    }

    @Override
    public void addObserver(IFriendListener iFriendListener) {
        observers.add(iFriendListener);
    }

    @Override
    public void dispatch(FriendEventType type, GenericFriend friend) {
        for (IFriendListener listener : observers) {
            switch (type) {
                case FRIEND_REMOVE:
                    listener.onFriendRemove(friend);
                    break;
                case FRIEND_IN:
                    listener.onIncomingFriendRequest(friend);
                    break;
                case FRIEND_OUT:
                    listener.onOutgoingFriendRequest(friend);
                    break;
                case INCOMING_REVOKED:
                    listener.onIncomingFriendRequestRevoked(friend);
                    break;
                case OUTGOING_CANCELED:
                    listener.onOutgoingFriendRequestCanceled(friend);
                    break;
                case OUTGOING_ACCEPTED:
                    listener.onOutgoingFriendRequestAccepted(friend);
                    break;
                case INCOMING_ACCEPTED:
                    listener.onIncomingFriendRequestAccepted(friend);
                    break;
            }
        }
    }

    private final Object lock = new Object();

    public List<GenericFriend> find(Predicate<GenericFriend> predicate) {
        return new ArrayList<>(map.values()).stream().flatMap(List::stream).filter(predicate).collect(Collectors.toList());
    }

    public GenericFriend singleMatchOrNull(Predicate<GenericFriend> predicate) {
        List<GenericFriend> list = new ArrayList<>(map.values()).stream().flatMap(List::stream).filter(predicate).collect(Collectors.toList());
        return list.size() == 1 ? list.get(0) : null;
    }

    public SubscriptionType find(GenericFriend friend) {
        synchronized (lock) {
            return new ArrayList<>(map.values()).stream().flatMap(List::stream).filter(o -> o.getPUUID().equals(friend.getPUUID())).map(GenericFriend::getType).findFirst().orElse(SubscriptionType.UNKNOWN);
        }
    }

    public void add(GenericFriend friend) {
        SubscriptionType type = find(friend);
        put(friend);
        Logger.info("[FRIEND] [+] {}:{}", type.name(), friend.type.name());
        if (type == SubscriptionType.UNKNOWN) {
            switch (friend.type) {
                case PENDING_IN:
                    dispatch(FriendEventType.FRIEND_IN, friend);
                    break;
                case PENDING_OUT:
                    dispatch(FriendEventType.FRIEND_OUT, friend);
                    break;
            }
        } else if (type == SubscriptionType.PENDING_OUT && friend.type == SubscriptionType.BOTH) {
            remove(SubscriptionType.PENDING_OUT, friend);
            dispatch(FriendEventType.OUTGOING_ACCEPTED, friend);
        } else if (type == SubscriptionType.PENDING_IN && friend.type == SubscriptionType.BOTH) {
            remove(SubscriptionType.PENDING_IN, friend);
            dispatch(FriendEventType.INCOMING_ACCEPTED, friend);
        }
    }

    private void put(GenericFriend friend) {
        if (friend instanceof OnlineFriend) cache.put(friend.getJID(), ((OnlineFriend) friend).getLOLName());
        synchronized (lock) {
            if (!map.containsKey(friend.getType())) map.put(friend.getType(), new ArrayList<>());
            map.get(friend.getType()).add(friend);
        }
    }

    public void remove(GenericFriend friend) {
        SubscriptionType type = find(friend);
        Logger.info("[FRIEND] [-] {}:{}", type.name(), friend.type.name());
        if (type == SubscriptionType.BOTH) {
            dispatch(FriendEventType.FRIEND_REMOVE, friend);
        } else if (type == SubscriptionType.PENDING_IN) {
            dispatch(FriendEventType.INCOMING_REVOKED, friend);
        } else if (type == SubscriptionType.PENDING_OUT) {
            dispatch(FriendEventType.OUTGOING_CANCELED, friend);
        }
        remove(type, friend);
    }

    private void remove(SubscriptionType type, GenericFriend friend) {
        synchronized (lock) {
            map.get(type).remove(friend);
        }
    }

    public String reverse(String name) {
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String resolve(String jid) {
        return cache.getOrDefault(jid, null);
    }

    public List<GenericFriend> getList(SubscriptionType type) {
        return map.getOrDefault(type, new ArrayList<>());
    }

    @Override
    public String toString() {
        return "FriendList{" + "map=" + map + '}';
    }
}
