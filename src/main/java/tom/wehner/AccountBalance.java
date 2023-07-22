package tom.wehner;


//import java.sql.Date;

import java.time.LocalDateTime;
import java.util.Date;

public class AccountBalance {

    private Integer id;
    private String accountName;
    private LocalDateTime timestamp;
    private float balance;

    public AccountBalance(Integer id, String accountName, LocalDateTime timestamp, float balance) {
        this.id = id;
        this.accountName = accountName;
        this.timestamp = timestamp;
        this.balance = balance;
    }

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
