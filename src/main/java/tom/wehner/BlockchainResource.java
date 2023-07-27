package tom.wehner;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.hyperledger.fabric.client.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;

@Path("/api/hyperledger")
public class BlockchainResource {

    @Inject
    BlockchainController controller;

    @Path("/transactions")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTransactions() throws GatewayException, CertificateException, IOException, InvalidKeyException, InterruptedException {

        return controller.getAllTransactions();

    }

    @Path("/history/{user}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHistory(@PathParam("user") String user) throws GatewayException, CertificateException, IOException, InvalidKeyException, InterruptedException, CommitException {

        return controller.getAssetHistory(user);

    }

    @Path("/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendPayment(@Valid Payment payment) throws EndorseException, CommitException, SubmitException, CommitStatusException, CertificateException, IOException, InvalidKeyException, InterruptedException {

        controller.transfer(payment);

    }

}
