package tom.wehner.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MongoBalanceRepository implements PanacheMongoRepository<MongoAccountBalance> {



}
