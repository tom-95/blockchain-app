package tom.wehner.mysql;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

//import java.sql.Date;

@Entity
@Table(name = "ACCOUNT_BALANCE")
public class AccountBalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BALANCE_ID")
    private Integer id;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @Column(name = "BALANCE")
    private float balance;

    public AccountBalanceEntity(String accountName, LocalDateTime timestamp, float balance) {
        this.accountName = accountName;
        this.timestamp = timestamp;
        this.balance = balance;
    }

    public AccountBalanceEntity() { }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getAccountName() { return accountName; }

    public void setAccountName(String accountName) { this.accountName = accountName; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public float getBalance() { return balance; }

    public void setBalance(float balance) { this.balance = balance; }
}
