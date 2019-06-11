package vehicletracker.io.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import vehicletracker.io.entity.Alerting;
import vehicletracker.io.entity.Vehicle;
import vehicletracker.io.service.VehicleService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "vehicles")
@CrossOrigin
public class VehicleController
{
    @Autowired
    VehicleService vehicleService;

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping
    public List<Vehicle> findAll()
    {
        return vehicleService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{vin}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Vehicle findOne(@ApiParam(value = "Id of the Vehicle", required = true)
                           @PathVariable("vin") String vehicleIN)
    {
        return vehicleService.findOne(vehicleIN);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/HIGH",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Vehicle> findByPriority()
    {
        return vehicleService.findAllByHighPriority();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/geo/{vin}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<List<HashMap>> AllGeolocation(@ApiParam(value = "Id of the Vehicle", required = true)
                                                  @PathVariable("vin") String vehicleIN)
    {
        return vehicleService.findAllGeolocation(vehicleIN);
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Vehicle create(@RequestBody Vehicle vehicle, Alerting alerting)
    {
        return vehicleService.create(vehicle);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{vin}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Vehicle update(@RequestBody Vehicle vehicle, @PathVariable String vin)
    {
        return vehicleService.update(vin, vehicle);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{vin}")
    public void delete(@PathVariable("vin") String vehicleIN)
    {
        vehicleService.delete(vehicleIN);
    }
}


