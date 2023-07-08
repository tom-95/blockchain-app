package tom.wehner.mongodb;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tom.wehner.Payment;

import java.util.List;

@Path("/api/mongo")
public class MongoResource {

    @Inject
    MongoRepository repository;

    @Path("/blockchain")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MongoEntity> getBlockchain() {

        return repository.findAll().list();

    }

    @Path("/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendPayment(@Valid Payment payment) {

        repository.persist(new MongoEntity(payment));

    }

}
