package pl.tabor.CarApi.controler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tabor.CarApi.model.Car;
import pl.tabor.CarApi.model.Color;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.tabor.CarApi.model.Color.*;

@RestController
@RequestMapping("/cars")
public class CarApi {

    private final List<Car> carList;


    public CarApi() {
        this.carList = new ArrayList<>();


        carList.add(new Car(1L, "BMW", "X5", RED));
        carList.add(new Car(2L, "Fiat", "126P", RED));
        carList.add(new Car(3L, "Ferrari", "F430", BLACK));
    }

    @GetMapping
    public ResponseEntity<Car> getCarList() {
        return new ResponseEntity(carList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {

        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarsByColor(@PathVariable Color color) {

        List<Car> newCarList = carList.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());

        if (!newCarList.isEmpty()) {
            return new ResponseEntity<>(newCarList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        boolean add = carList.add(car);

        if (add) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping
    public ResponseEntity<Car> modCar(@RequestBody Car newCar) {

        Optional<Car> first = carList.stream().filter(car1 -> car1.getId() == newCar.getId()).findFirst();

        if (first.isPresent()) {
            carList.remove(first.get());
            carList.add(newCar);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PatchMapping("update/{id}")
    public ResponseEntity updateCar(@PathVariable Long id, @RequestBody Car updateCar) {

        Optional<Car> currentCarData = carList.stream().filter(car -> car.getId() == id).findFirst();

        if (currentCarData.isPresent()) {

            if (updateCar.getModel() != null) currentCarData.get().setModel(updateCar.getModel());
            if (updateCar.getMark() != null) currentCarData.get().setMark(updateCar.getMark());
            if (updateCar.getColor() != null) currentCarData.get().setColor(updateCar.getColor());

            return new ResponseEntity(updateCar, HttpStatus.OK);
        }
        return new ResponseEntity(updateCar, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCarById(@PathVariable Long id) {

        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            carList.remove(first.get());

            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.GONE);
    }


}

