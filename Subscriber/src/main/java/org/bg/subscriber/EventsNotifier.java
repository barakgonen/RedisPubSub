package org.bg.subscriber;

import org.bg.subscriber.redis.SubscribedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Class responsible for notifying events for subscribed clients
 */
public class EventsNotifier {

    private HashMap<String, Set<SubscribedClient>> eventsToClients;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventsNotifier.class);
    private int numberOfHandledMsgs;
    private final ExecutorService executor;

    public EventsNotifier() {
        eventsToClients = new HashMap<>();
        this.numberOfHandledMsgs = 0;
        this.executor = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
    }

    public void subscribeToEvent(String eventId, SubscribedClient subscribedClient) {
        if (!this.eventsToClients.containsKey(eventId)) {
            this.eventsToClients.put(eventId, Collections.synchronizedSet(new HashSet<>()));
            // Call to REDIS subscribe for eventID
        }
        this.eventsToClients.get(eventId).add(subscribedClient);
    }

    public void unsubscribeFromEvent(String eventId, SubscribedClient subscribedClient) {
        if (this.eventsToClients.containsKey(eventId)) {
            if (this.eventsToClients.get(eventId).remove(subscribedClient)){
                System.out.println("Unsubscribed successfully!");
            }
            if (this.eventsToClients.get(eventId).isEmpty()) {
                // CALL UNSUBSCRIBE from REDIS to THAT pattern
                // remove that entry, because it has no listeners
            }
        }
    }

    private void handleIncomingData(String key, String value) {
        numberOfHandledMsgs++;
        if (numberOfHandledMsgs % 100000 == 0)
            LOGGER.info("Until now, handled: {} msgs", numberOfHandledMsgs);
        var d = this.eventsToClients.get(key);
        if (d != null) {
            Runnable runnableTask = () -> {
                d.forEach(s -> {
                    s.handleIncomingEvent(value);
                });
            };
            executor.execute(runnableTask);
//            this.eventsToClients.get(key).forEach(s -> {
//                Thread publicationThread = new Thread(() -> s.handleIncomingEvent(value));
//                publicationThread.start();
//            });
        }
    }

    public void handleMsg(String key, String value) {
        handleIncomingData(key, value);
    }
}
