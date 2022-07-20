package io.evilgeniuses.energy_optimization;


import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EnergyDataPointRepository extends MongoRepository<EnergyDataPoint,String> {

    List<EnergyDataPoint> findBySource(String source);
}
