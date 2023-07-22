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
public class AccountBalanceRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional(Transactional.TxType.NEVER)
    public List<AccountBalanceEntity> getHistory(String accountName) {

        String queryString = "select entity from AccountBalanceEntity entity where entity.accountName like :account";

        Query query = entityManager.createQuery(queryString).setParameter("account", accountName);
        return query.getResultList();
    }

    @Transactional(Transactional.TxType.NEVER)
    public AccountBalanceEntity getNewest(String accountName) {

        String queryString = "select entity from AccountBalanceEntity entity where entity.accountName like :account ORDER BY entity.timestamp DESC";

        Query query = entityManager.createQuery(queryString).setParameter("account", accountName).setMaxResults(1);
        List<AccountBalanceEntity> result = query.getResultList();

        return result.get(0);
    }

    public int add(AccountBalanceEntity payment) {
        entityManager.persist(payment);
        return payment.getId();
    }

}
