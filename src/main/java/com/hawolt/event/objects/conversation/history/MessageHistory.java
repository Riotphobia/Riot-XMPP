package com.hawolt.event.objects.conversation.history;

import com.hawolt.event.BaseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: 11/04/2022 18:10
 * Author: Twitter @hawolt
 **/

public class MessageHistory extends BaseObject {

    private final List<AbstractMessage> list = new ArrayList<>();

    private String id;

    public void add(AbstractMessage conversation) {
        this.list.add(conversation);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<AbstractMessage> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "MessageHistory{" +
                "list=" + list +
                ", id='" + id + '\'' +
                '}';
    }
}
