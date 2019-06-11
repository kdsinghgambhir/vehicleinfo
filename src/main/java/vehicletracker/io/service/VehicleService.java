package vehicletracker.io.service;

import vehicletracker.io.entity.Vehicle;

import java.util.HashMap;
import java.util.List;

public interface VehicleService
{
    List<Vehicle> findAll();

    List<Vehicle> findAllByHighPriority();

    List<List<HashMap>> findAllGeolocation(String vin);

    Vehicle findOne(String vin);

    Vehicle create(Vehicle vehicle);

    Vehicle update(String id, Vehicle vehicle);

    void delete(String vin);
}
