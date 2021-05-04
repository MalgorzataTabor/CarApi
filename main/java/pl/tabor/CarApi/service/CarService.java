package pl.tabor.CarApi.service;

import org.springframework.stereotype.Service;
import pl.tabor.CarApi.model.Car;


import java.util.List;
import java.util.Optional;



public interface CarService {

    Optional<List<Car>> getAllCars();
    boolean addCar(Car car);
    boolean deleteCar(long id);
    Optional<Car> getCarById(long id);
    List<Car>getCarsByColor(String color);

    Boolean updateCar(long id, Car updateCar);
}
