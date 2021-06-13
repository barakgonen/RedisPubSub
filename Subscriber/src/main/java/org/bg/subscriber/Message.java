package org.bg.subscriber;

public class Message<T> {
    private String id;
    private long sentDateTimeMillis;
    private long receiveDateTimeMillis;
    private long totalOffset;
    private T payload;

    public static <T> Message<T> create(String id, T payload){
        Message<T> instance = new Message<>();
        instance.id = id;
        instance.sentDateTimeMillis = System.currentTimeMillis();
        instance.payload = payload;
        return instance;
    }

    public void setReceiveDateTimeMillis() {
        receiveDateTimeMillis = System.currentTimeMillis();
        totalOffset = receiveDateTimeMillis - sentDateTimeMillis;
    }

    public T getPayload() {
        return payload;
    }
    public long getTotalOffset(){
        return totalOffset;
    }
}
