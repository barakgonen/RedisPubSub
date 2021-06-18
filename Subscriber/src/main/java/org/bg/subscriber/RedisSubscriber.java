package org.bg.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

import java.time.Instant;
import java.util.Collection;

public class RedisSubscriber extends JedisPubSub implements SubscriberApi {
    private static Logger logger = LoggerFactory.getLogger(RedisSubscriber.class);

    private int id;
    private int msgNumber;
    private Collection<String> initialState;
    private long lastMessurementTime;


    public RedisSubscriber(int id) {
        super();
        this.id = id;
        this.msgNumber = 0;
        this.initialState = initialState;
        lastMessurementTime = Instant.now().getEpochSecond();
    }

    @Override
    public void subscribe(String key) {
        super.subscribe(key);
    }

    @Override
    public void unsubscribe(String key) {
        super.unsubscribe(key);
    }

    @Override
    public void subscribe(Collection<String> keys) {
        super.subscribe(keys.toArray(new String[keys.size()]));
    }

    @Override
    public void unsubscribe(Collection<String> keys) {
        super.unsubscribe(keys.toArray(new String[keys.size()]));
    }

    @Override
    public void onMessage(String channel, String message) {
        msgNumber++;
        if (msgNumber % 1000 == 0) {
            long nowTime = Instant.now().getEpochSecond();
            long diff = nowTime - lastMessurementTime;
            lastMessurementTime = nowTime;
            System.out.println("Client: " + id + " total msgs: " + msgNumber + ", diff from last msg millis: " + diff);
            System.out.println("");
        }
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
//        System.out.println("Client " + id + " is Subscribed to channel : " + channel);
//        System.out.println("Client " + id + " is Subscribed to " + subscribedChannels + " no. of channels");
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("Client is Unsubscribed from channel : " + channel);
        System.out.println("Client is Subscribed to " + subscribedChannels + " no. of channels");
    }
}