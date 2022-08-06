package com.hawolt.event.objects.conversation.hint;

import com.hawolt.event.BaseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: 18/04/2022 01:04
 * Author: Twitter @hawolt
 **/

public class RecentConversations extends BaseObject {
    private final List<AbstractConversation> list = new ArrayList<>();

    public void add(AbstractConversation conversation) {
        this.list.add(conversation);
    }

    public List<AbstractConversation> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "RecentConversations{" +
                "list=" + list +
                '}';
    }
}
