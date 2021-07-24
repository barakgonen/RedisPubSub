package org.bg.subscriber;

import com.google.gson.Gson;
import compressors.Message;
import compressors.SnappyCompressor;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.config.Config;

import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class RedisSubscriber implements SubscriberApi, Runnable {
    private int id;
    private int msgNumber;
    private RedissonClient client;
    private ConcurrentHashMap<String, RTopic> msgs;
    Gson GSON = new Gson();
    private int expectedNumberOfMessages;
    boolean hasFinished;

    private SnappyCompressor<Message> decompressor;
    private HashSet<String> myKeys;

    private ConcurrentHashMap<String, MessageListener<String>> bg;

    public RedisSubscriber(int id, int numberOfMessages, Config config) {
        this.id = id;
        this.msgNumber = 0;
        this.client = Redisson.create(config);
        this.msgs = new ConcurrentHashMap<>();
        this.expectedNumberOfMessages = numberOfMessages;
        this.hasFinished = false;
        this.bg = new ConcurrentHashMap<>();
        this.decompressor = new SnappyCompressor<>();
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    @Override
    public void subscribe(String key) {
        RTopic topic = client.getTopic(key);
//        topic.addListener();
        topic.addListenerAsync(String.class, this::onMessageArrived);
        msgs.put(key, topic);
    }

    @Override
    public void unsubscribe(String key) {

//        super.unsubscribe(key);
    }

    private void onMessageArrived(CharSequence channel, String msg) {
        msgNumber++;
        try {
            byte[] value = Base64.getDecoder().decode(msg);
            Message message = decompressor.uncompress(value);
            message.setReceiveDateTimeMillis();
            if (message.getTotalOffset() > 2)
                System.out.println("ClientId = " + id + ", channel = " + channel + ", diff millis: " + message.getTotalOffset());
            if (msgNumber % 10000 == 0)
                System.out.println("Client: " + id + ", got: " + msgNumber + " msgs");
            if (msgNumber == this.expectedNumberOfMessages)
                hasFinished = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public HashSet<String> getMyKeys() {
        return myKeys;
    }

    @Override
    public void subscribe(HashSet<String> keys) {
        myKeys = keys;
//        super.subscribe(keys.toArray(new String[keys.size()]));
    }

    @Override
    public void unsubscribe(HashSet<String> keys) {
        keys.forEach(s -> {
            msgs.get(s).removeAllListeners();
        });
//        super.unsubscribe(keys.toArray(new String[keys.size()]));
    }

//    @Override
//    public void onMessage(String channel, String message) {
//        msgNumber++;
//        System.out.println("Client: " + id + " msg number: " + msgNumber);
//    }
//
//    @Override
//    public void onSubscribe(String channel, int subscribedChannels) {
//        System.out.println("Client " + id + " is Subscribed to channel : " + channel);
//        System.out.println("Client " + id + " is Subscribed to " + subscribedChannels + " no. of channels");
//    }
//
//    @Override
//    public void onUnsubscribe(String channel, int subscribedChannels) {
//        System.out.println("Client is Unsubscribed from channel : " + channel);
//        System.out.println("Client is Subscribed to " + subscribedChannels + " no. of channels");
//    }
//    }

    @Override
    public void run() {
        myKeys.forEach(key -> {
            RTopic topic = client.getTopic(key);
            topic.addListener(String.class, this::onMessageArrived);
            msgs.put(key, topic);
        });
    }
}