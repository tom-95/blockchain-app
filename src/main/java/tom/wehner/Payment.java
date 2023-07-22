package tom.wehner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


public class Payment {

    @NotBlank
    private String sender;
    @NotBlank
    private String receiver;
    @Positive
    private float amount;

    public Payment(String sender, String receiver, float amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public String getSender() { return sender; }

    public String getReceiver() { return receiver; }

    public float getAmount() { return amount; }

}
