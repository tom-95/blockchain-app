package tom.wehner;

import java.util.ArrayList;
import java.util.List;

public class Block {

    private int previousHash;
    private List<Transaction> transactions;
    private int blockHash;

    public Block(int previousHash, List<Transaction> transactions) {
        this.previousHash = previousHash;
        this.transactions = transactions;

        List<Integer> content = List.of(transactions.hashCode(), previousHash);
        this.blockHash = content.hashCode();
    }

    public int getPreviousHash() { return previousHash; }

    public List<Transaction> getTransactions() { return transactions; }

    public int getBlockHash() { return blockHash; }

}
