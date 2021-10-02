package org.bg.subscriber.redis;

import java.util.Collection;

public interface SubscriberApi {
    public void subscribe(String key);
    public void unsubscribe(String key);
    public void subscribe(Collection<String> keys);
    public void unsubscribe(Collection<String> keys);
}
