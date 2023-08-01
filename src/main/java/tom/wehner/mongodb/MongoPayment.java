package tom.wehner.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

import java.time.LocalDateTime;

public class MongoPayment extends PanacheMongoEntity {

    private String sender;
    private String receiver;
    private String purpose;
    private float amount;

    public MongoPayment() { }

    public MongoPayment(String sender, String receiver, String purpose, float amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.purpose = purpose;
        this.amount = amount;
    }

    public String getSender() { return sender; }

    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getPurpose() { return purpose; }

    public void setPurpose(String purpose) { this.purpose = purpose; }

    public float getAmount() { return amount; }

    public void setAmount(float amount) { this.amount = amount; }
}
