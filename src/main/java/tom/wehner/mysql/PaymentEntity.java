package tom.wehner.mysql;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//import java.sql.Date;

@Entity
@Table(name = "PAYMENT")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Integer id;

    @Column(name = "SENDER")
    private String sender;

    @Column(name = "RECEIVER")
    private String receiver;

    @Column(name = "PURPOSE")
    private String purpose;

    @Column(name = "AMOUNT")
    private float amount;

    public PaymentEntity(String sender, String receiver, String purpose, float amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.purpose = purpose;
        this.amount = amount;
    }

    public PaymentEntity() { }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getSender() { return sender; }

    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getPurpose() { return purpose; }

    public void setPurpose(String purpose) { this.purpose = purpose; }

    public float getAmount() { return amount; }

    public void setAmount(float amount) { this.amount = amount; }
}
