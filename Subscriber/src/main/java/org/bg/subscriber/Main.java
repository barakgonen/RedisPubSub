package org.bg.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

public class Main {
    private static final int MIN_CHANNEL_ID = 0;
    private static final int MAX_CHANNEL_ID = 30000;
    private static final int EXPECTED_SIZE_FOR_CLIENT = 15000;
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        int numberOfClients = 8;
        ExecutorService executor = Executors.newFixedThreadPool(20);

        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        final JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
        ConcurrentHashMap<Integer, MyRedisClient> idToClient = new ConcurrentHashMap<>();

        for (int i = 0; i < numberOfClients; i++) {
            idToClient.put(i, new MyRedisClient(i, jedisPool.getResource()));
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        idToClient.values().forEach(myRedisClient -> {
            myRedisClient.subscribe(getChannelIds());
        });

        final JedisPoolConfig poolConfig2 = new JedisPoolConfig();
        final JedisPool jedisPool2 = new JedisPool(poolConfig2, "localhost", 6379, 0);
        ConcurrentHashMap<Integer, MyRedisClient> idToClient2 = new ConcurrentHashMap<>();

        for (int i = 20; i < numberOfClients + 20; i++) {
            idToClient2.put(i, new MyRedisClient(i, jedisPool2.getResource()));
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        idToClient2.values().forEach(myRedisClient -> {
            myRedisClient.subscribe(getChannelIds());
        });



        final JedisPoolConfig poolConfig3 = new JedisPoolConfig();
        final JedisPool jedisPool3 = new JedisPool(poolConfig3, "localhost", 6379, 0);
        ConcurrentHashMap<Integer, MyRedisClient> idToClient3 = new ConcurrentHashMap<>();

        for (int i = 30; i < numberOfClients + 30; i++) {
            idToClient3.put(i, new MyRedisClient(i, jedisPool3.getResource()));
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        idToClient3.values().forEach(myRedisClient -> {
            myRedisClient.subscribe(getChannelIds());
        });



        final JedisPoolConfig poolConfig4 = new JedisPoolConfig();
        final JedisPool jedisPool4 = new JedisPool(poolConfig4, "localhost", 6379, 0);
        ConcurrentHashMap<Integer, MyRedisClient> idToClient4 = new ConcurrentHashMap<>();

        for (int i = 40; i < numberOfClients + 40; i++) {
            idToClient4.put(i, new MyRedisClient(i, jedisPool4.getResource()));
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        idToClient4.values().forEach(myRedisClient -> {
            myRedisClient.subscribe(getChannelIds());
        });


        final JedisPoolConfig poolConfig5 = new JedisPoolConfig();
        final JedisPool jedisPool5 = new JedisPool(poolConfig5, "localhost", 6379, 0);
        ConcurrentHashMap<Integer, MyRedisClient> idToClient5 = new ConcurrentHashMap<>();

        for (int i = 50; i < numberOfClients + 50; i++) {
            idToClient5.put(i, new MyRedisClient(i, jedisPool5.getResource()));
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        idToClient5.values().forEach(myRedisClient -> {
            myRedisClient.subscribe(getChannelIds());
        });

        final JedisPoolConfig poolConfig6 = new JedisPoolConfig();
        final JedisPool jedisPool6 = new JedisPool(poolConfig6, "localhost", 6379, 0);
        ConcurrentHashMap<Integer, MyRedisClient> idToClient6 = new ConcurrentHashMap<>();

        for (int i = 60; i < numberOfClients + 60; i++) {
            idToClient6.put(i, new MyRedisClient(i, jedisPool6.getResource()));
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            idToClient6.values().forEach(myRedisClient -> {
        myRedisClient.subscribe(getChannelIds());
    });

        final JedisPoolConfig poolConfig7 = new JedisPoolConfig();
        final JedisPool jedisPool7 = new JedisPool(poolConfig7, "localhost", 6379, 0);
        ConcurrentHashMap<Integer, MyRedisClient> idToClient7 = new ConcurrentHashMap<>();

        for (int i = 70; i < numberOfClients + 70; i++) {
            idToClient7.put(i, new MyRedisClient(i, jedisPool7.getResource()));
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
                idToClient7.values().forEach(myRedisClient -> {
            myRedisClient.subscribe(getChannelIds());
        });

        final JedisPoolConfig poolConfig8 = new JedisPoolConfig();
        final JedisPool jedisPool8 = new JedisPool(poolConfig8, "localhost", 6379, 0);
        ConcurrentHashMap<Integer, MyRedisClient> idToClient8 = new ConcurrentHashMap<>();

        for (int i = 80; i < numberOfClients + 80; i++) {
            idToClient7.put(i, new MyRedisClient(i, jedisPool8.getResource()));
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        idToClient8.values().forEach(myRedisClient -> {
            myRedisClient.subscribe(getChannelIds());
        });
        System.out.println("BGBGBGBGB");

    }

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

