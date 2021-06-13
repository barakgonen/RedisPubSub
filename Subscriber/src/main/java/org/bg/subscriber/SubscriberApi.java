package org.bg.subscriber;

import java.util.Collection;
import java.util.HashSet;

public interface SubscriberApi {
    public void subscribe(String key);
    public void unsubscribe(String key);
    public void subscribe(HashSet<String> keys);
    public void unsubscribe(HashSet<String> keys);
}
