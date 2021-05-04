package pl.tabor.CarApi.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.tabor.CarApi.model.Car;

import java.util.ArrayList;
import java.util.List;

import static pl.tabor.CarApi.model.Color.BLACK;
import static pl.tabor.CarApi.model.Color.RED;


@Repository
public class CarRepository {

    private List<Car> carList;

    @Autowired
    public CarRepository() {
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
}
