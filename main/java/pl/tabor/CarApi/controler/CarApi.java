package pl.tabor.CarApi.controler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.tabor.CarApi.model.Car;
import pl.tabor.CarApi.model.Color;
import pl.tabor.CarApi.service.CarService;

import java.util.List;


@RestController()
@RequestMapping("/cars")
public class CarApi {

    private CarService carService;


    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }


    @GetMapping(produces = {
            //  MediaType.APPLICATION_ATOM_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<List<Car>> getCars() {
        if (carService.getAllCars().isPresent()) {

            return ResponseEntity.ok(carService.getAllCars().get());
        } else {
            return ResponseEntity.notFound().build();
        }

        /*carList.forEach(car -> car.add(linkTo(CarApi.class).slash(car.getId()).withSelfRel()));

        return new ResponseEntity(carList, HttpStatus.OK);*/
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {

        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

       /* Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();

        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);*/
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarsByColor(@PathVariable Color color) {


        List<Car> carsByColor = carService.getCarsByColor(String.valueOf(color));

        if (!carsByColor.isEmpty()) {
            return ResponseEntity.ok(carsByColor);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    @PostMapping
    public ResponseEntity<Car> addCar(@Validated @RequestBody Car car, BindingResult bindingResult) {
        boolean isAdd = carService.addCar(car);

        if (isAdd) {
            carService.addCar(car);
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<Car> modCar(@RequestBody Car newCar, BindingResult bindingResult) {

        boolean isDeleted = carService.deleteCar(newCar.getId());
        boolean isUpdate = carService.addCar(newCar);

        if (isDeleted && isUpdate) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    @PatchMapping("/{id}")
    public ResponseEntity updateCarProperty(@PathVariable long id, @RequestBody Car updateCar) {


        boolean isUpdated = carService.updateCar(id, updateCar);

        if (isUpdated) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCarById(@PathVariable long id) {
        boolean isCarDeleted = carService.deleteCar(id);

        if (isCarDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }


}

