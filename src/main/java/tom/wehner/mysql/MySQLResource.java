package tom.wehner.mysql;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tom.wehner.AccountBalance;
import tom.wehner.Payment;

import java.util.List;

@Path("/api/mysql")
public class MySQLResource {

    @Inject
    PaymentController controller;

    @Path("/history")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccountBalance> getHistory() {

        return controller.getHistory();

    }

    @Path("/latest")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccountBalance> getLatest() {

        return controller.getLatest();

    }

    @Path("/initiate")
    @POST
    public void initiate() {

        controller.initiate();

    }

    @Path("/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void transfer(@Valid Payment payment) {

        controller.transfer(payment);

    }

}
