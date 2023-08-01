package tom.wehner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


public class Payment {

    private Integer id;
    @NotBlank
    private String sender;
    @NotBlank
    private String receiver;
    @NotBlank
    private String purpose;
    @Positive
    private float amount;

    public Payment(String sender, String receiver, String purpose, float amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.purpose = purpose;
        this.amount = amount;
    }

    public Payment(Integer id, String sender, String receiver, String purpose, float amount) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.purpose = purpose;
        this.amount = amount;
    }

    public Payment() { }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getSender() { return sender; }

    public String getReceiver() { return receiver; }

    public String getPurpose() { return purpose; }

    public float getAmount() { return amount; }

}
