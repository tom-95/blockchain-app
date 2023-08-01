package tom.wehner.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional(Transactional.TxType.MANDATORY)
public class PaymentRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional(Transactional.TxType.NEVER)
    public List<PaymentEntity> getAll() {

        String queryString = "select entity from PaymentEntity entity";

        Query query = entityManager.createQuery(queryString);
        List<PaymentEntity> result = query.getResultList();

        return result;
    }

    public int add(PaymentEntity payment) {
        entityManager.persist(payment);
        return payment.getId();
    }

}
