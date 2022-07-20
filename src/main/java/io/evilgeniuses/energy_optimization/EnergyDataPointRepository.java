package io.evilgeniuses.energy_optimization;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item,String> {

}
