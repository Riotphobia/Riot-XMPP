package com.hawolt.event.events;

import com.hawolt.logger.Logger;
import com.hawolt.event.AbstractEvent;
import com.hawolt.event.GenericEvent;
import com.hawolt.event.objects.friends.Friend;
import com.hawolt.event.objects.friends.FriendList;
import com.hawolt.event.objects.friends.GenericFriend;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created: 11/04/2022 00:11
 * Author: Twitter @hawolt
 **/

public class FriendListEvent extends GenericEvent<FriendList> {

    private final static Pattern pattern = Pattern.compile("(<item.*?</item>)");

    private final FriendList list;

    public FriendListEvent(AbstractEvent event) {
        super(event);
        Matcher matcher = pattern.matcher(event.getPlain());
        List<GenericFriend> list = new ArrayList<>();
        while (matcher.find()) {
            try {
                JSONObject object = XML.toJSONObject(matcher.group(0)).getJSONObject("item");
                list.add(Friend.create(object));
            } catch (Exception e) {
                Logger.error(e);
            }
        }
        this.list = new FriendList(list.toArray(new GenericFriend[0]));
    }

    @Override
    public FriendList get() {
        return list;
    }
}
