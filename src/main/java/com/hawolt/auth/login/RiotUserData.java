package com.hawolt.auth.login;

import org.json.JSONObject;

/**
 * Created: 10/04/2022 14:55
 * Author: Twitter @hawolt
 **/

public class RiotUserData {

    private final JSONObject object;

    public RiotUserData(JSONObject object) {
        this.object = object;
    }

    public String get(LoginValue value) {
        return object.getString(value.toString());
    }
}
