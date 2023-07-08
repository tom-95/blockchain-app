package tom.wehner.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import tom.wehner.Payment;

public class MongoEntity extends PanacheMongoEntity {

    private String sender;
    private String receiver;
    private String purpose;
    private float amount;

    public MongoEntity() { }

    public MongoEntity(String sender, String receiver, String purpose, float amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.purpose = purpose;
        this.amount = amount;
    }

    public MongoEntity(Payment payment) {
        this.sender = payment.getSender();
        this.receiver = payment.getReceiver();
        this.purpose = payment.getPurpose();
        this.amount = payment.getAmount();
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getPurpose() {
        return purpose;
    }

    public float getAmount() {
        return amount;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
