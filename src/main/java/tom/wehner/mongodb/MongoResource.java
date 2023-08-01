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
    MongoBalanceRepository balanceRepository;
    @Inject
    MongoPaymentRepository paymentRepository;

    @Path("/history")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MongoAccountBalance> getHistory() {

        return balanceRepository.list("accountName", "account_tom");

    }

    @Path("/initiate")
    @POST
    public void initiate() {

        balanceRepository.persist(new MongoAccountBalance("account_tom", LocalDateTime.now(), 10000));
        balanceRepository.persist(new MongoAccountBalance("account_vermieter", LocalDateTime.now(), 0));

    }

    @Path("/transactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MongoPayment> getTransactions() {

        return paymentRepository.listAll();

    }

    @Path("/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendPayment(@Valid Payment payment) {

        List<MongoAccountBalance> list = balanceRepository.list("accountName", "account_" + payment.getSender().toLowerCase());
        list.sort((entity1, entity2) -> entity2.getTimestamp().compareTo(entity1.getTimestamp()));
        MongoAccountBalance latestSender = list.get(0);

        list = balanceRepository.list("accountName", "account_" + payment.getReceiver().toLowerCase());
        list.sort((entity1, entity2) -> entity2.getTimestamp().compareTo(entity1.getTimestamp()));
        MongoAccountBalance latestReceiver = list.get(0);

        balanceRepository.persist(new MongoAccountBalance("account_" + payment.getSender().toLowerCase(), LocalDateTime.now(), latestSender.getBalance() - payment.getAmount()));
        balanceRepository.persist(new MongoAccountBalance("account_" + payment.getReceiver().toLowerCase(), LocalDateTime.now(), latestReceiver.getBalance() + payment.getAmount()));
        paymentRepository.persist(new MongoPayment(payment.getSender(), payment.getReceiver(), payment.getPurpose(), payment.getAmount()));

    }

}
