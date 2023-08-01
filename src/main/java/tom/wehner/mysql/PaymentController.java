package tom.wehner.mysql;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import jakarta.ws.rs.InternalServerErrorException;
import tom.wehner.AccountBalance;
import tom.wehner.Payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestScoped
public class PaymentController {

    @Inject
    AccountBalanceRepository accountBalanceRepository;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    UserTransaction transaction;

    public List<AccountBalance> getHistory() {

        final List<AccountBalance> history = new ArrayList<>();
        accountBalanceRepository.getHistory("account_tom").forEach(entity -> history.add(new AccountBalance(entity.getId(), entity.getAccountName(), entity.getTimestamp(), entity.getBalance())));
        return history;
    }

    public List<Payment> getTransactions() {

        List<Payment> result = new ArrayList<>();
        paymentRepository.getAll().forEach(entity -> result.add(new Payment(entity.getId(), entity.getSender(), entity.getReceiver(), entity.getPurpose(), entity.getAmount())));
        return result;
    }

    public void initiate() {

        try {
            transaction.begin();
            accountBalanceRepository.add(new AccountBalanceEntity("account_tom", LocalDateTime.now(), 10000));
            accountBalanceRepository.add(new AccountBalanceEntity("account_vermieter", LocalDateTime.now(), 0));
            transaction.commit();
        } catch (EntityExistsException e) {
            throw new AlreadyExistsException("Unable to create payment", e);
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (Exception ex) {
                throw new InternalServerErrorException("Unable to rollback on create payment", ex);
            }
            throw new InternalServerErrorException("Unable to create payment", e);
        }
    }

    public void transfer(final Payment payment) {
        float senderBalanceOld = accountBalanceRepository.getNewest("account_" + payment.getSender().toLowerCase()).getBalance();
        float receiverBalanceOld = accountBalanceRepository.getNewest("account_" + payment.getReceiver().toLowerCase()).getBalance();
        AccountBalanceEntity sender = new AccountBalanceEntity();
        sender.setAccountName("account_" + payment.getSender().toLowerCase());
        sender.setTimestamp(LocalDateTime.now());
        sender.setBalance(senderBalanceOld - payment.getAmount());
        AccountBalanceEntity receiver = new AccountBalanceEntity();
        receiver.setAccountName("account_" + payment.getReceiver().toLowerCase());
        receiver.setTimestamp(LocalDateTime.now());
        receiver.setBalance(receiverBalanceOld + payment.getAmount());
        try {
            transaction.begin();
            accountBalanceRepository.add(sender);
            accountBalanceRepository.add(receiver);
            paymentRepository.add(new PaymentEntity(payment.getSender(), payment.getReceiver(), payment.getPurpose(), payment.getAmount()));
            transaction.commit();
        } catch (EntityExistsException e) {
            throw new AlreadyExistsException("Unable to create payment", e);
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (Exception ex) {
                throw new InternalServerErrorException("Unable to rollback on create payment", ex);
            }
            throw new InternalServerErrorException("Unable to create payment", e);
        }
    }

}
