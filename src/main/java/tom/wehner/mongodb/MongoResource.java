package tom.wehner.mongodb;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tom.wehner.Payment;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Path("/api/mongo")
public class MongoResource {

    @Inject
    MongoRepository repository;

    @Path("/history")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MongoAccountBalance> getHistory() {

        return repository.list("accountName", "account_tom");

    }

    @Path("/initiate")
    @POST
    public void initiate() {

        repository.persist(new MongoAccountBalance("account_tom", LocalDateTime.now(), 10000));
        repository.persist(new MongoAccountBalance("account_vermieter", LocalDateTime.now(), 0));

    }

    @Path("/latest")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MongoAccountBalance> getLatest() {

        List<MongoAccountBalance> result = new ArrayList<>();

        List<MongoAccountBalance> list = repository.list("accountName", "account_tom");
        list.sort((entity1, entity2) -> entity2.getTimestamp().compareTo(entity1.getTimestamp()));
        MongoAccountBalance latestSender = list.get(0);

        list = repository.list("accountName", "account_vermieter");
        list.sort((entity1, entity2) -> entity2.getTimestamp().compareTo(entity1.getTimestamp()));
        MongoAccountBalance latestReceiver = list.get(0);

        result.add(latestSender);
        result.add(latestReceiver);

        return result;

    }

    @Path("/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendPayment(@Valid Payment payment) {

        List<MongoAccountBalance> list = repository.list("accountName", "account_" + payment.getSender().toLowerCase());
        list.sort((entity1, entity2) -> entity2.getTimestamp().compareTo(entity1.getTimestamp()));
        MongoAccountBalance latestSender = list.get(0);

        list = repository.list("accountName", "account_" + payment.getReceiver().toLowerCase());
        list.sort((entity1, entity2) -> entity2.getTimestamp().compareTo(entity1.getTimestamp()));
        MongoAccountBalance latestReceiver = list.get(0);

        repository.persist(new MongoAccountBalance("account_" + payment.getSender().toLowerCase(), LocalDateTime.now(), latestSender.getBalance() - payment.getAmount()));
        repository.persist(new MongoAccountBalance("account_" + payment.getReceiver().toLowerCase(), LocalDateTime.now(), latestReceiver.getBalance() + payment.getAmount()));

    }

}
