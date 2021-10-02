package org.bg.subscriber.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Collection;

public class MyRedisClient {
    private static Logger logger = LoggerFactory.getLogger(MyRedisClient.class);

    private final Jedis subscriberJedisTwo;
    private final RedisSubscriber subscriber2;
    private final int clientId;

    public MyRedisClient(int clientId, Jedis client){
        this.clientId = clientId;
        this.subscriberJedisTwo = client;
        subscriber2 = new RedisSubscriber(this.clientId);
    }

    public void subscribe(Collection<String> keys){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("Subscribing to \"commonChannel\". This thread will be blocked.");
                    subscriberJedisTwo.subscribe(subscriber2, keys.toArray(new String[keys.size()]));
                    logger.info("Subscription ended.");
                } catch (Exception e) {
                    logger.error("Subscribing failed.", e);
                }
            }
        }).start();
    }

    public void unsubscribe(Collection<String> keys){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("Subscribing to \"commonChannel\". This thread will be blocked.");
                    subscriberJedisTwo.unlink(keys.toArray(new String[keys.size()]));
                    logger.info("Subscription ended.");
                } catch (Exception e) {
                    logger.error("Subscribing failed.", e);
                }
            }
        }).start();
    }
}