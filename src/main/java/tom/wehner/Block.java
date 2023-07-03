package tom.wehner;

import java.util.List;

public class Block {

    private int id;
    private int previousHash;
    private List<Transaction> transactions;
    private int blockHash;

    public Block(int id, int previousHash, List<Transaction> transactions) {
        this.id = id;
        this.previousHash = previousHash;
        this.transactions = transactions;

        List<Integer> content = List.of(transactions.hashCode(), previousHash);
        this.blockHash = content.hashCode();
    }

    public int getId() { return id; }

    public int getPreviousHash() { return previousHash; }

    public List<Transaction> getTransactions() { return transactions; }

    public int getBlockHash() { return blockHash; }

}
