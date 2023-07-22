package tom.wehner.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import tom.wehner.Payment;

import java.sql.Date;
import java.time.LocalDateTime;

public class MongoAccountBalance extends PanacheMongoEntity {

    private String accountName;
    private LocalDateTime timestamp;
    private float balance;

    public MongoAccountBalance() { }

    public MongoAccountBalance(String accountName, LocalDateTime timestamp, float balance) {
        this.accountName = accountName;
        this.timestamp = timestamp;
        this.balance = balance;
    }

//    public MongoAccountBalance(Payment payment) {
//        this.sender = payment.getSender();
//        this.receiver = payment.getReceiver();
//        //this.purpose = payment.getPurpose();
//        this.amount = payment.getAmount();
//    }

    public String getAccountName() {
        return accountName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public float getBalance() {
        return balance;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
