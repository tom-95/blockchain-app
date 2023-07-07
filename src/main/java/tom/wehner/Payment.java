package tom.wehner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


public class Payment {


    private int id;
    @NotBlank
    private String sender;
    @NotBlank
    private String receiver;
    @NotBlank
    private String purpose;
    @Positive
    private float amount;

    public Payment(int id, String sender, String receiver, String purpose, float amount) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.purpose = purpose;
        this.amount = amount;
    }

    public String getSender() { return sender; }

    public String getReceiver() { return receiver; }

    public String getPurpose() { return purpose; }

    public float getAmount() { return amount; }

}
