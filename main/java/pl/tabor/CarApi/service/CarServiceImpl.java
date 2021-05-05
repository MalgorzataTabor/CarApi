package pl.tabor.CarApi.service;


import org.springframework.stereotype.Service;
import pl.tabor.CarApi.model.Car;
import pl.tabor.CarApi.model.Color;

import pl.tabor.CarApi.repository.CarRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.tabor.CarApi.model.Color.BLACK;
import static pl.tabor.CarApi.model.Color.RED;


@Service
public class CarServiceImpl implements CarRepository {


    private List<Car> carList;

    public CarServiceImpl() {
        carList = new ArrayList<>();
        initCarList();
    }


    private void initCarList() {
        carList.add(new Car(1L, "BMW", "X5", RED));
        carList.add(new Car(2L, "Fiat", "126P", RED));
        carList.add(new Car(3L, "Ferrari", "F430", BLACK));
    }

    public List<Car> getCarList() {
        return carList;
    }


    @Override
    public Optional<List<Car>> getAllCars() {
        return Optional.ofNullable(carList);
    }


    @Override
    public boolean deleteCar(long id) {
        boolean isRemoved;
        Optional<Car> car = carList.stream()
                .filter(car1 -> car1.getId() == id)
                .findFirst();

        isRemoved = car.map(value -> carList.remove(value)).orElse(false);
        return isRemoved;
    }

    @Override
    public Optional<Car> getCarById(long id) {
        return carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();

    }

    @Override
    public boolean addCar(Car car) {
        boolean isAdded;

        Optional<Car> carWithTheSameId = carList.stream()
                .filter(car1 -> car1.getId() == car.getId())
                .findFirst();

        if (carWithTheSameId.isPresent()) {
            isAdded = false;
        } else {
            isAdded = carList.add(car);
        }
        return isAdded;

    }


    @Override
    public List<Car> getCarsByColor(String color) {
        return carList.stream().filter(car -> car.getColor() == Color.valueOf(color))
                .collect(Collectors.toList());
    }


    @Override
    public Boolean updateCar(long id, Car updateCar) {

        Optional<Car> optionalCar = carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();

        if (optionalCar.isPresent()) {

            if (updateCar.getModel() != null) optionalCar.get().setModel(updateCar.getModel());
            if (updateCar.getMark() != null) optionalCar.get().setMark(updateCar.getMark());
            if (updateCar.getColor() != null) optionalCar.get().setColor(updateCar.getColor());
            return true;
        }

        return false;
    }
}
