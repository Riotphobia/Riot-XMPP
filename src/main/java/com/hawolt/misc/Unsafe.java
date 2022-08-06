package com.hawolt.misc;

/**
 * Created: 30/04/2022 11:12
 * Author: Twitter @hawolt
 **/

public class Unsafe {
    @SuppressWarnings(value = "all")
    public static <T> T cast(Object o) {
        return (T) o;
    }
}
