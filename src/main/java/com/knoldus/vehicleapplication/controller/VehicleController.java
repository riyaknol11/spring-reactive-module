package com.knoldus.vehicleapplication.controller;

import com.knoldus.vehicleapplication.dto.Vehicle;
import com.knoldus.vehicleapplication.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private VehicleRepository vehicleRepository;
    @Autowired
    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/data")
    public Flux<Vehicle> getData() {
        WebClient webClient = WebClient.create();
        String apiUrl = "https://my.api.mockaroo.com/vehicle.json?key=0090e7f0";

        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(Vehicle.class);
    }

    @GetMapping("/allbrands")
    public Flux<String> getBrands() {
        return vehicleRepository.getAllBrands();
    }
    @GetMapping("/{brand}")
    public Flux<Vehicle> getCarsByBrand(@PathVariable String brand) {
        return vehicleRepository.findByBrand(brand);
    }

}
