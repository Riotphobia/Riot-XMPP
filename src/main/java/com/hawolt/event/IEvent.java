package com.hawolt.event;

/**
 * Created: 10/04/2022 23:52
 * Author: Twitter @hawolt
 **/

public interface IEvent {
    void onEvent(AbstractEvent event);
}
