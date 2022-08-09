package io.evilgeniuses.energy_optimization.repositories;


import io.evilgeniuses.energy_optimization.dataclasses.EnergyDataPoint;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EnergyDataPointRepository extends MongoRepository<EnergyDataPoint,String> {

    List<EnergyDataPoint> findBySource(String source);
    List<EnergyDataPoint> findAllBySource(String source);
    EnergyDataPoint findByEndTimeStampAndSource(DateTime endTimeStamp, String source);
    void deleteBySource(String source);
}
