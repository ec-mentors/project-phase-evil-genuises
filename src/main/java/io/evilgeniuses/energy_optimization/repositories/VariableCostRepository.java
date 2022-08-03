package io.evilgeniuses.energy_optimization.repositories;

import io.evilgeniuses.energy_optimization.dataclasses.VariableCost;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VariableCostRepository extends MongoRepository<VariableCost,String> {

    Optional<VariableCost> findByEndTimeStamp(long endTimeStamp);
}
