package tom.wehner.mongodb;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import tom.wehner.Payment;

import java.util.List;

@RequestScoped
public class MongoController {

    @Inject
    MongoRepository repository;

    public List<MongoEntity> getPayments() {

        return repository.findAll().list();
    }

    public void createPayment(final Payment payment) {

        repository.persist(new MongoEntity(payment));
    }

}
