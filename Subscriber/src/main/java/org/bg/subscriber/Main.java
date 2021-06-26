package org.bg.subscriber;

import compressors.SnappyCompressor;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.HashSet;
import java.util.Random;

public class Main {
    private static final int MIN_CHANNEL_ID = 0;
    private static final int MAX_CHANNEL_ID = 1;
    private static final int EXPECTED_SIZE_FOR_CLIENT = 1;

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

    private static void onMessageArrived(CharSequence channel, String msg) {
        System.out.println("received msg is: " + msg);
        try {
            byte[] value = Base64.getDecoder().decode(msg);
            Message fromData = new SnappyCompressor<Message>().uncompress(value);
            fromData.setReceiveDateTimeMillis();
            if (fromData.getTotalOffset() > 2)
                System.out.println(/*"ClientId = " + id + ", */"channel = " + channel + ", diff millis: " + fromData.getTotalOffset());
//        if (msgNumber % 10 == 0)
//            System.out.println("Client: " + id + ", got: " + msgNumber + " msgs");
//        if (msgNumber == this.expectedNumberOfMessages)
//            hasFinished = true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        Message<String> message = GSON.fromJson(msg, Message.class);

    }

    public static void main(String[] args) throws IOException {
        RedissonClient client = Redisson.create();
        RTopic subscribeTopic = client.getTopic("Channel_0");
        subscribeTopic.addListener(String.class, Main::onMessageArrived);

//        int numberOfClients = Runtime.getRuntime().availableProcessors() * 2;
//        int numberOfClients = 2;
//        ExecutorService executor = Executors.newFixedThreadPool(numberOfClients);
//        InputStream inputStream = Main.class.getResourceAsStream("/singleNodeConfig.json");
//        String value = readFromInputStream(inputStream);
//        ConcurrentHashMap<Integer, RedisSubscriber> subscribers = new ConcurrentHashMap<>();
//        Config config = Config.fromJSON(value);
//
//        for (int i = 0; i < numberOfClients; i++) {
//            RedisSubscriber subscriber = new RedisSubscriber(i, 500, config);
//            subscriber.subscribe(getChannelIds());
////            new Thread(subscriber).start();
//            subscribers.put(i, subscriber);
//            executor.submit(subscriber);
//        }
//
//        boolean allHasFinished = false;
//
//        while (!allHasFinished) {
//            boolean currentState = true;
//            for (int i = 0; i < numberOfClients; i++) {
//                currentState = currentState && subscribers.get(i).hasFinished();
//            }
//
//            allHasFinished = currentState;
//        }
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

