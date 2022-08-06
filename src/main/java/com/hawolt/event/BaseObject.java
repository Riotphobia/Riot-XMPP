package com.hawolt.event;

import org.json.JSONObject;
import org.json.XML;

/**
 * Created: 14/04/2022 02:41
 * Author: Twitter @hawolt
 **/

public class BaseObject {
    private final long timestamp = System.currentTimeMillis();

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "timestamp=" + timestamp +
                '}';
    }
}
