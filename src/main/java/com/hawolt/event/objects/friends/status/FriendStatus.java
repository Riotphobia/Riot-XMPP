package com.hawolt.event.objects.friends.status;

import com.hawolt.event.BaseObject;
import org.json.JSONObject;

/**
 * Created: 12/04/2022 02:06
 * Author: Twitter @hawolt
 **/

public class FriendStatus extends BaseObject {

    private final FriendStatusAction action;
    private final FriendStatusType type;
    private final String id;

    public FriendStatus(JSONObject o) {
        this.id = o.getString("id");
        this.type = FriendStatusType.of(o.getString("type"));
        this.action = FriendStatusAction.of(o.getString("id").split("_")[1]);
    }

    public FriendStatusAction getAction() {
        return action;
    }

    public FriendStatusType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "FriendStatus{" +
                "action=" + action +
                ", type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
