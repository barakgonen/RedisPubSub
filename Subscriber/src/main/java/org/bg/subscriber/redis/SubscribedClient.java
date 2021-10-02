package org.bg.subscriber.redis;

import org.bg.subscriber.EventsNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents streaming requst from client to get information by push.
 */
public class SubscribedClient {

    private String clientName;
    private EventsNotifier notifier;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscribedClient.class);
    private int numberOfSubscribedEntities;
    private int numberOfHandledEvents;

    public SubscribedClient(EventsNotifier notifier, String clientName) {
        this.clientName = clientName;
        this.notifier = notifier;
        this.numberOfSubscribedEntities = 0;
        this.numberOfHandledEvents = 0;
    }

    public void handleIncomingEvent(String s) {
        numberOfHandledEvents++;
        if (numberOfHandledEvents % 100000 == 0)
            LOGGER.info("HandlerForClientId: {}, pushing the following event to client: {}, until now, handled: {} events", clientName, s, numberOfHandledEvents);
    }

    public String toString() {
        return "My name is: " + clientName + ", i'm subscribed to: " + numberOfSubscribedEntities + " entities";
    }

    // TODO: remove the following because they should be part of WebSocketServer
    public void subscribeToData(String key) {
//        LOGGER.info("Client: {}, is subscribing to the following key: {}", clientName, key);
        notifier.subscribeToEvent(key, this);
        numberOfSubscribedEntities++;
    }

    public void unsubscribeFromData(String key) {
//        LOGGER.info("Client: {}, is unsubscribing from the following key: {}", clientName, key);
        notifier.unsubscribeFromEvent(key, this);
        numberOfSubscribedEntities--;
        if (numberOfSubscribedEntities < 0)
            LOGGER.error("Number of subscribed entities is negative, there is an error!!!");
    }
}
