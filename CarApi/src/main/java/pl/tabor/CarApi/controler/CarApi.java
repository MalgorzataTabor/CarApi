package pl.tabor.CarApi.controler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.tabor.CarApi.model.Car;
import pl.tabor.CarApi.model.Color;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.tabor.CarApi.model.Color.*;

@RestController
@RequestMapping("/cars")
public class CarApi {

    private List<Car> carList;


    public CarApi() {
        this.carList = new ArrayList<>();


        carList.add(new Car(1l, "Adi", "Q5", GREEN));
        carList.add(new Car(2l, "Wolksvagen", "Passat", YELLOW));
        carList.add(new Car(3l, "Ferrari", "daczer", RED));
    }

    @GetMapping
    public ResponseEntity<Car> getCarList() {
        return new ResponseEntity(carList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {

        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();

        if (first.isPresent()) {
            return new ResponseEntity<Car>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

/*    @GetMapping("/{color}")
    public  ResponseEntity<List<Car>>getCarsByColor(@PathVariable Color color){
        List<Car> newCarList = carList.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());

            if(!newCarList.isEmpty()){
                return new ResponseEntity<>(newCarList, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }*/

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
/*
    @PatchMapping
    public ResponseEntity<Car>modOneElementCar(@RequestBody Car modCar){

        Optional<Car> first = carList.stream().filter(car -> car.getId() == modCar.getId()).findFirst();

        if(first.isPresent()){

        }
    }*/

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

/*
        do pobierania elementów w określonym kolorze (query)


        do modyfikowania jednego z pól pozycji
        do usuwania jeden pozycji*/
