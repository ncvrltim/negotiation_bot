package com.demo.controller;

import com.demo.entity.Car;
import com.demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;
    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<String> listCarForSale(@RequestBody Car car) {
        car.setSoldOut(false);
        carRepository.save(car);
        return ResponseEntity.ok("Car listed for sale successfully.");
    }
    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<List<Car>> getAllAvailableCars(@RequestParam(name = "soldOut") boolean soldOut) {
        List<Car> cars = carRepository.findCarsBySoldOutIs(soldOut);
        return ResponseEntity.ok(cars);
    }
}
