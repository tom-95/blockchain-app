package tom.wehner.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

@ApplicationScoped
@Transactional(Transactional.TxType.MANDATORY)
public class PaymentRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional(Transactional.TxType.NEVER)
    public List<PaymentEntity> getAll() {

        String queryString = "select payment from PaymentEntity payment";

        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }

    public int add(@Valid final PaymentEntity payment) {
        entityManager.persist(payment);
        return payment.getId();
    }

}
