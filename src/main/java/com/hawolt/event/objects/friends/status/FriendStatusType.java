package com.hawolt.event.objects.friends.status;

/**
 * Created: 23/04/2022 22:42
 * Author: Twitter @hawolt
 **/

public enum FriendStatusType {
    RESULT, ERROR, UNKNOWN;

    private static final FriendStatusType[] FRIEND_STATUS_TYPES = FriendStatusType.values();

    public static FriendStatusType of(String o) {
        for (FriendStatusType type : FRIEND_STATUS_TYPES) {
            if (type.name().equalsIgnoreCase(o)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
