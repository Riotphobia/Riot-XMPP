# Riot-XMPP

this library is a headless implementation of the XMPP derivative which is being used by Riot Games.

---

Here is some example code to get you started while I am working on documenting the code

- args[0] : username
- args[1] : password

the following code will connect to the suggested Chat Server and log information about your own Identity once connected and afterwards attempts to add the player `Name#Tagline` which as of today does not exist and results in an error. In addition, it will log information about all of your friends once the friend list has loaded.

```java
import com.hawolt.auth.RiotUser;
import com.hawolt.event.EventListener;
import com.hawolt.event.EventType;
import com.hawolt.event.objects.friends.FriendList;
import com.hawolt.event.objects.friends.SubscriptionType;
import com.hawolt.logger.Logger;
import com.hawolt.xmpp.RiotXMPPAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ClientExample {
    public static void main(String[] args) {
        RiotUser user = new RiotUser(args[0], args[1]);
        RiotXMPPAccount account = new RiotXMPPAccount(user);
        account.connect().whenComplete((client, throwable) -> {
            if (throwable != null) Logger.error(throwable);
            else {
                client.addHandler(EventType.ON_READY, event -> {
                    Logger.info("I am {}", client.getIdentity());
                    client.addFriendByTag("Name", "Tagline", status -> {
                        Logger.info("Friend Add Response {}", status);
                    });
                });
                client.addHandler(EventType.FRIEND_LIST, new EventListener<FriendList>() {
                    @Override
                    public void onEvent(FriendList list) {
                        Arrays.stream(SubscriptionType.values())
                                .map(type -> new ArrayList<>(list.getList(type)))
                                .flatMap(Collection::stream)
                                .forEach(Logger::info);
                    }
                });
            }
        });
    }
}
```
