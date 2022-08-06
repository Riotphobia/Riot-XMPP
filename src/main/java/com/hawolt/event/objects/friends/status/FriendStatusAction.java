package com.hawolt.event.objects.friends.status;

/**
 * Created: 23/04/2022 22:42
 * Author: Twitter @hawolt
 **/

public enum FriendStatusAction {
    ADD, REMOVE, UNKNOWN;

    private static final FriendStatusAction[] FRIEND_STATUS_ACTIONS = FriendStatusAction.values();

    public static FriendStatusAction of(String o) {
        for (FriendStatusAction type : FRIEND_STATUS_ACTIONS) {
            if (type.name().equalsIgnoreCase(o)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
