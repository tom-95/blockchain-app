package tom.wehner;

import java.util.Objects;

public class Transaction {

    private String sender;
    private String receiver;
    private float amount;

    public Transaction(String sender, String receiver, float amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public String getSender() { return sender; }

    public String getReceiver() { return receiver; }

    public float getAmount() { return amount; }

//    @Override
//    public boolean equals(Object o) {
//
//        if (o == this) {
//            return true;
//        }
//
//        if (!(o instanceof Transaction transaction)) {
//            return false;
//        }
//
//        return sender.equals(transaction.getSender()) && receiver.equals(transaction.getReceiver()) && (0 == Float.compare(amount, transaction.getAmount()));
//    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receiver, amount);
    }

}
