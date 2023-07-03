package tom.wehner;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/api")
public class BlockchainService {

    List<Block> blockchain = new ArrayList<>();

    @Path("/blockchain")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Block> getBlockchain() {

        return blockchain;

    }

    @Path("/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendTransaction(@Valid Transaction transaction) {

        if (blockchain.size() == 0) {

            Block genesisBlock = new Block(0, 0, List.of(transaction));
            blockchain.add(genesisBlock);

        } else {

            Block block = new Block(blockchain.size(), blockchain.get(blockchain.size()-1).getBlockHash(), List.of(transaction));
            blockchain.add(block);

        }

    }

}
