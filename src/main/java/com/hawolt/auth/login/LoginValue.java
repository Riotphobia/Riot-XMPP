package com.hawolt.auth.login;

/**
 * Created: 10/04/2022 15:09
 * Author: Twitter @hawolt
 **/

public enum LoginValue {
    ACCESS_TOKEN, SCOPE, ISS, ID_TOKEN, TOKEN_TYPE, SESSION_STATE, EXPIRES_IN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
