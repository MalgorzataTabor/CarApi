package pl.tabor.CarApi.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.tabor.CarApi.model.Car;
import pl.tabor.CarApi.model.Color;
import pl.tabor.CarApi.service.CarServiceImpl;


import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController()
@RequestMapping("/cars")
public class CarApi {

    private final CarServiceImpl carService;


    @Autowired
    public CarApi(CarServiceImpl carService) {
        this.carService = carService;
    }


    @GetMapping(produces = {
            //  MediaType.APPLICATION_ATOM_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<List<Car>> getCars() {
        List<Car> carModels = carService.getCarList();
        if (!carModels.isEmpty()) {

            carModels.forEach(car -> car.addIf(!car.hasLinks(), () -> linkTo(CarApi.class).slash(car.getId()).withSelfRel()));
            Link link = linkTo(CarApi.class).withSelfRel();

            return new ResponseEntity<List<Car>>(carService.getAllCars().get(), HttpStatus.FOUND);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {

        return carService.getCarById(id)
                .map(car -> {
                    addLinkToCar(car);
                    return ResponseEntity.ok(car);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarsByColor(@PathVariable Color color) {


        List<Car> carsByColor = carService.getCarsByColor(String.valueOf(color));

        if (!carsByColor.isEmpty()) {
            carService.getCarList().forEach(car -> car.addIf(!car.hasLinks(), () -> linkTo(CarApi.class).slash(car.getId()).withSelfRel()));
            Link link = linkTo(CarApi.class).withSelfRel();
            return ResponseEntity.ok(carsByColor);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<Car> addCar(@Validated @RequestBody Car car) {
        boolean isAdd = carService.addCar(car);

        if (isAdd) {
            carService.addCar(car);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Car> modCar(@Validated @RequestBody Car newCar) {

        boolean isDeleted = carService.deleteCar(newCar.getId());
        boolean isUpdate = carService.addCar(newCar);

        if (isDeleted && isUpdate) {
            addLinkToCar(newCar);
            return ResponseEntity.ok(newCar);
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
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.notFound().build();

    }

    private void addLinkToCar(Car car) {
        car.add(linkTo(CarApi.class).slash(car.getId()).withSelfRel());
    }


}

