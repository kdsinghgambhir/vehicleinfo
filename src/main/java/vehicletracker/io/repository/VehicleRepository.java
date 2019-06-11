package vehicletracker.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vehicletracker.io.entity.Vehicle;

import java.util.Optional;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, String>
{

}
