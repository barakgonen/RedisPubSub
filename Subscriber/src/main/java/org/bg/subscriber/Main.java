package org.bg.subscriber;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class Main {
    private static final int MIN_CHANNEL_ID = 0;
    private static final int MAX_CHANNEL_ID = 30000;
    private static final int EXPECTED_SIZE_FOR_CLIENT = 3000;

    public static void main(String[] args) {
        int numberOfClients = 30;

        RedisSubscriber subscriber = new RedisSubscriber(1);

        subscriber.subscribe(getChannelIds());

        while (true) {

        }
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

