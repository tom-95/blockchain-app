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

    @Path("/blockchain")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getBlockchain() throws GatewayException, CertificateException, IOException, InvalidKeyException, InterruptedException {

        return controller.getAllAssets();

    }

    @Path("/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendPayment(@Valid Payment payment) throws EndorseException, CommitException, SubmitException, CommitStatusException, CertificateException, IOException, InvalidKeyException, InterruptedException {

        controller.createAsset(payment);

    }

}
