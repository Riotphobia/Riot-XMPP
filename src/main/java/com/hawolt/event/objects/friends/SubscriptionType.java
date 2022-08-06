package com.hawolt.event.objects.friends;

/**
 * Created: 14/04/2022 07:18
 * Author: Twitter @hawolt
 **/

public enum SubscriptionType {
    BOTH, REMOVE, PENDING_IN, PENDING_OUT, UNKNOWN;

    private static final SubscriptionType[] SUBSCRIPTION_TYPES = SubscriptionType.values();

    public static SubscriptionType of(String subscription) {
        for (SubscriptionType type : SUBSCRIPTION_TYPES) {
            if (type.name().equalsIgnoreCase(subscription)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
