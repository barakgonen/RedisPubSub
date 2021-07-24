package org.bg.subscriber;

import compressors.Message;
import compressors.SnappyCompressor;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int MIN_CHANNEL_ID = 0;
    private static final int MAX_CHANNEL_ID = 5000;
    private static final int EXPECTED_SIZE_FOR_CLIENT = 2500;

    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static void main(String[] args) throws IOException {
        int numberOfClients = Runtime.getRuntime().availableProcessors() * 2;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfClients);
        InputStream inputStream = Main.class.getResourceAsStream("/singleNodeConfig.json");
        String value = readFromInputStream(inputStream);
        ConcurrentHashMap<Integer, RedisSubscriber> subscribers = new ConcurrentHashMap<>();
        Config config = Config.fromJSON(value);

        for (int i = 0; i < numberOfClients; i++) {
            RedisSubscriber subscriber = new RedisSubscriber(i, 100, config);
            subscriber.subscribe(getChannelIds());
//            new Thread(subscriber).start();
            subscribers.put(i, subscriber);
            executor.submit(subscriber);
        }
//
        boolean allHasFinished = false;

        while (!allHasFinished) {
            boolean currentState = true;
            for (int i = 0; i < numberOfClients; i++) {
                currentState = currentState && subscribers.get(i).hasFinished();
            }

            allHasFinished = currentState;
        }
    }

    private static HashSet<String> getChannelIds() {
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

