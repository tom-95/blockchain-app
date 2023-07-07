package tom.wehner.mysql;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.UserTransaction;
import jakarta.ws.rs.InternalServerErrorException;
import tom.wehner.Payment;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class PaymentController {

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    UserTransaction transaction;

    public List<Payment> getPayments() {

        final List<Payment> payments = new ArrayList<>();
        paymentRepository.getAll().forEach(entity -> payments.add(new Payment(entity.getId(), entity.getSender(), entity.getReceiver(), entity.getPurpose(), entity.getAmount())));
        return payments;
    }

    public int createPayment(final Payment payment) {
        final PaymentEntity entity = new PaymentEntity();
        entity.setSender(payment.getSender());
        entity.setReceiver(payment.getReceiver());
        entity.setPurpose(payment.getPurpose());
        entity.setAmount(payment.getAmount());
        try {
            transaction.begin();
            int id = paymentRepository.add(entity);
            transaction.commit();
            return id;
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
