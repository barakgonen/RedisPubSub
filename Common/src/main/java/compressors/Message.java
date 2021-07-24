package compressors;

import java.io.Serializable;
import java.util.Objects;

public class Message<T> implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message<?> message = (Message<?>) o;
        return sentDateTimeMillis == message.sentDateTimeMillis &&
                receiveDateTimeMillis == message.receiveDateTimeMillis &&
                totalOffset == message.totalOffset &&
                Objects.equals(id, message.id) &&
                Objects.equals(payload, message.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sentDateTimeMillis, receiveDateTimeMillis, totalOffset, payload);
    }

    public long getTotalOffset(){
        return this.totalOffset;
    }
}
