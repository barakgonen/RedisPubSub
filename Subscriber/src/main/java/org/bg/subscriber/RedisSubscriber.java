package org.bg.subscriber;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Collection;

public class RedisSubscriber extends JedisPubSub implements SubscriberApi {
    private int id;
    private int msgNumber;
    private Jedis jedis;


    public RedisSubscriber(int id) {
        this.id = id;
        this.msgNumber = 0;
        this.jedis = new Jedis();
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
        System.out.println("Client: " + id + " msg number: " + msgNumber);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("Client " + id + " is Subscribed to channel : " + channel);
        System.out.println("Client " + id + " is Subscribed to " + subscribedChannels + " no. of channels");
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("Client is Unsubscribed from channel : " + channel);
        System.out.println("Client is Subscribed to " + subscribedChannels + " no. of channels");
    }
}