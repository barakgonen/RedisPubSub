package org.bg.subscriber;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int MIN_CHANNEL_ID = 0;
    private static final int MAX_CHANNEL_ID = 30000;
    private static final int EXPECTED_SIZE_FOR_CLIENT = 3000;

    public static void main(String[] args) {
        int numberOfClients = 20;
        ExecutorService executor = Executors.newFixedThreadPool(20);

        Jedis jedis = null;
//        try {
            jedis = new Jedis();
//            ConcurrentHashMap<Integer, RedisSubscriber> subs = new ConcurrentHashMap<>();
//
//            for (int i = 0; i < numberOfClients; i++){
//                subs.put(i, new RedisSubscriber(i, jedis, getChannelIds()));
//            }
//
//            subs.entrySet().forEach(integerRedisSubscriberEntry -> {
//                executor.submit(integerRedisSubscriberEntry.getValue());
//            });
//
//
//            while (true){
//
//            }
//        } catch (Exception e){
//            System.out.println("Exception has caught");
//        }

//            ConcurrentHashMap<Integer, JedisPubSub> subscribers = new ConcurrentHashMap<>();
////            try {
//            for (int i = 0; i < numberOfClients; i++) {
        JedisPubSub jedisPubSub = new JedisPubSub() {

            private int numberOfMessages = 0;

            @Override
            public void onMessage(String channel, String message) {
                numberOfMessages++;
//                    System.out.println("Channel " + channel + " has sent a message : " + message);
                if (numberOfMessages % 100 == 0)
                    System.out.println("BGBGBGBGBGBGB");
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                System.out.println("Client is Subscribed to channel : " + channel);
                System.out.println("Client is Subscribed to " + subscribedChannels + " no. of channels");
            }

            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {
                System.out.println("Client is Unsubscribed from channel : " + channel);
                System.out.println("Client is Subscribed to " + subscribedChannels + " no. of channels");
            }

        };

        Collection<String> keys = getChannelIds();
        jedis.subscribe(jedisPubSub, keys.toArray(keys.toArray(new String[keys.size()])));

//                subscribers.put(i, jedisPubSub);
    }
//
//
//            /* Creating Jedis object for connecting with redis server */
//            /* Creating JedisPubSub object for subscribing with channels */
//
//            for (int i = 0; i < subscribers.size(); i++) {
//                Collection<String> entities = getChannelIds();
//                jedis.subscribe(subscribers.get(i), entities.toArray(new String[entities.size()]));
//            }
//
//            /* Subscribing to channel C1 and C2 */
//
//        } catch (Exception ex) {
//
//            System.out.println("Exception : " + ex.getMessage());
//
//        } finally {
//
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//    }


    private static Collection<String> getChannelIds() {
        HashSet<String> ids = new HashSet<>();
        while (ids.size() < EXPECTED_SIZE_FOR_CLIENT) {
            ids.add(getChannelName(getRandomNumberUsingNextInt()));
        }

        return ids;
    }

    private static int getRandomNumberUsingNextInt() {
        Random random = new Random();
        return random.nextInt(MAX_CHANNEL_ID - MIN_CHANNEL_ID) + MIN_CHANNEL_ID;
    }

    private static String getChannelName(int channelId) {
        return "Channel_" + channelId;
    }
}

