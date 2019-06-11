package vehicletracker.io.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vehicletracker.io.entity.Alerting;
import vehicletracker.io.entity.Vehicle;
import vehicletracker.io.exception.BadRequestException;
import vehicletracker.io.exception.ResourceNotFoundException;
import vehicletracker.io.repository.VehicleRepository;

import java.util.*;

@Service
public class VehicleServiceImplementation implements VehicleService
{

    /*List<List<Float>> latitudeList = new ArrayList<List<Float>>();
    List<List<Float>> longitudeList = new ArrayList<List<Float>>();
    List<Float> listLat = new ArrayList<>();
    List<Float> listLong = new ArrayList<>();*/
    List<List<HashMap>> latLongList = new ArrayList<List<HashMap>>();
    List<HashMap> list = new ArrayList<>();

    int counter = 0;

    @Autowired
    private VehicleRepository repository;

    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        return (List<Vehicle>) repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findAllByHighPriority()
    {
        List<Vehicle> vehicles = (List<Vehicle>) repository.findAll();
        List<Vehicle> vehiclesWithHighPriority = new ArrayList<>();
        for(Vehicle vehicle1 : vehicles)
        {
            if(vehicle1.getAlerts().getPRIORITY().equals("HIGH"))
            {
                vehiclesWithHighPriority.add(vehicle1);
            }
        }
        return vehiclesWithHighPriority;
    }

    @Transactional(readOnly = true)
    public List<List<HashMap>> findAllGeolocation(String vin)
    {
        List<List<HashMap>> result = new ArrayList<List<HashMap>>();
        Optional<Vehicle> vehicle1 = repository.findById(vin);
        int ListNum = vehicle1.get().getCount()-1;
        result.add(latLongList.get(ListNum));
        return result;

        /*List<List<Float>> result = new ArrayList<List<Float>>();
        Optional<Vehicle> vehicle1 = repository.findById(vin);
        int ListNum = vehicle1.get().getCount()-1;
        List<Float> list1 = new ArrayList<>();
        list1 = latitudeList.get(ListNum);
        List<Float> list2 = new ArrayList<>();
        list2 = longitudeList.get(ListNum);
        result.add(list1);
        result.add(list2);
        return result;*/
    }

    @Transactional(readOnly = true)
    public Vehicle findOne(String vin) {
        Optional<Vehicle> vehicle1 = repository.findById(vin);
        if(!vehicle1.isPresent())
        {
            throw new ResourceNotFoundException("NOT FOUND WITH THIS ID");
        }
        return vehicle1.get();

    }

    @Transactional
    public Vehicle create(Vehicle vehicle) {
        Optional<Vehicle> vehicle1 = repository.findById(vehicle.getVin());
        if(vehicle1.isPresent())
        {
            throw new BadRequestException("Vehicle Already Exists");
        }
        else
        {
            counter++;
            vehicle.setCount(counter);
            /*listLat.add(vehicle.getLatitude());
            listLong.add(vehicle.getLongitude());
            latitudeList.add(listLat);
            longitudeList.add(listLong);*/
            HashMap<Float, Float> map = new HashMap<>();
            map.put(vehicle.getLatitude(), vehicle.getLongitude());
            list.add(map);
            latLongList.add(list);
            return repository.save(vehicle);
        }
    }

    @Transactional
    public Vehicle update(String vin, Vehicle vehicle)
    {
        Optional<Vehicle> vehicle1 = repository.findById(vin);
        if(!vehicle1.isPresent())
        {
            throw new ResourceNotFoundException("Employer NOT FOUND");
        }

        Vehicle existingVehicle = new Vehicle();
        existingVehicle = repository.findById(vin).get();








        if(vehicle.getRedlineRpm() != 0)
        {
            existingVehicle.setRedlineRpm(vehicle.getRedlineRpm());
        }
        if(vehicle.getLastServiceDate() != null)
        {
            existingVehicle.setLastServiceDate(vehicle.getLastServiceDate());
        }
        if(vehicle.getMake() != null)
        {
            existingVehicle.setMake(vehicle.getMake());
        }
        if(vehicle.getModel() != null)
        {
            existingVehicle.setModel(vehicle.getModel());
        }
        if(vehicle.getYear() != 0)
        {
            existingVehicle.setYear(vehicle.getYear());
        }
        if(vehicle.getMaxFuelVolume() != 0 )
        {
            existingVehicle.setMaxFuelVolume(vehicle.getMaxFuelVolume());
        }



        if(vehicle.getTimestamp() != null)
        {
            existingVehicle.setTimestamp(vehicle.getTimestamp());
        }
        if(vehicle.getFuelVolume() != 0)
        {
            existingVehicle.setFuelVolume(vehicle.getFuelVolume());
        }
        if(vehicle.getSpeed() != 0 ||
                (vehicle.getSpeed() == 0
                && vehicle.getLatitude() == existingVehicle.getLatitude()
                && vehicle.getLongitude() == existingVehicle.getLongitude())
        )
        {
            existingVehicle.setSpeed(vehicle.getSpeed());
        }
        if(vehicle.getEngineHp() != 0)
        {
            existingVehicle.setEngineHp(vehicle.getEngineHp());
        }
        if(vehicle.getEngineRpm() != 0)
        {
            existingVehicle.setEngineRpm(vehicle.getEngineRpm());
        }
        if(vehicle.getLatitude() != null || vehicle.getLongitude() != null)
        {
            int listNum = vehicle.getCount();

            HashMap<Float, Float> map = new HashMap<>();
            map.put(vehicle.getLatitude(), vehicle.getLongitude());
            list = latLongList.get(listNum);
            list.add(map);
            latLongList.add(list);
            existingVehicle.setLatitude(vehicle.getLatitude());
            existingVehicle.setLongitude(vehicle.getLongitude());

/*            listLat = latitudeList.get(listNum);
            listLong = longitudeList.get(listNum);
            listLat.add(vehicle.getLatitude());
            listLong.add(vehicle.getLongitude()); */
        }


        Alerting alertPriority = new Alerting();
        alertPriority.setPRIORITY("NULL");
        existingVehicle.setAlerts(alertPriority);

        if(existingVehicle.getTires().getFrontLeft() < 32 || existingVehicle.getTires().getFrontLeft() > 36
                || existingVehicle.getTires().getFrontRight() < 32 || existingVehicle.getTires().getFrontRight() > 36
                || existingVehicle.getTires().getRearLeft() < 32 || existingVehicle.getTires().getRearLeft() > 36
                || existingVehicle.getTires().getRearRight() < 32 || existingVehicle.getTires().getRearRight() > 36
        )
        {
            Alerting alerting3 = new Alerting();
            alerting3.setPRIORITY("LOW");
            existingVehicle.setAlerts(alerting3);
        }

        if(existingVehicle.isEngineCoolantLow() || existingVehicle.isCheckEngineLightOn() )
        {
            Alerting alerting4 = new Alerting();
            alerting4.setPRIORITY("LOW");
            existingVehicle.setAlerts(alerting4);
        }

        if(existingVehicle.getFuelVolume() < (0.10 * existingVehicle.getMaxFuelVolume()))
        {
            Alerting alerting2 = new Alerting();
            alerting2.setPRIORITY("MEDIUM");
            existingVehicle.setAlerts(alerting2);
        }

        if(existingVehicle.getEngineRpm() > existingVehicle.getRedlineRpm())
        {
            Alerting alerting1 = new Alerting();
            alerting1.setPRIORITY("HIGH");
            existingVehicle.setAlerts(alerting1);
        }

        return repository.save(existingVehicle);
    }

    @Transactional
    public void delete(String vin) {
        Optional<Vehicle> vehicle = repository.findById(vin);
        if(!vehicle.isPresent())
        {
            throw new ResourceNotFoundException("Employee NOT FOUND");
        }
        repository.delete(vehicle.get());
    }
}
