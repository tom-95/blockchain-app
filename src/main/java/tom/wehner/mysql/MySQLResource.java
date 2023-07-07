package tom.wehner.mysql;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tom.wehner.Payment;

import java.util.List;

@Path("/api/mysql")
public class MySQLResource {

    @Inject
    PaymentController controller;

    @Path("/blockchain")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public List<Payment> getBlockchain() {

        return controller.getPayments();

    }

    @Path("/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendPayment(@Valid Payment payment) {

        controller.createPayment(payment);

    }

}
