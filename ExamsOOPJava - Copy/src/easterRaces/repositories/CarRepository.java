package easterRaces.repositories;

import easterRaces.entities.cars.Car;
import easterRaces.repositories.interfaces.Repository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static easterRaces.common.ExceptionMessages.CAR_NOT_FOUND;

public class CarRepository<E> implements Repository<Car> {
    private Collection<Car> models;

    public CarRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public Car getByName(String name) {
        for (Car car : this.models) {
            if (car.getModel().equals(name)) {
                return car;
            }
        }
        throw new IllegalArgumentException(String.format(CAR_NOT_FOUND,name));
    }

    @Override
    public Collection<Car> getAll() {
        return Collections.unmodifiableCollection(this.models);
    }

    @Override
    public void add(Car model) {
        this.models.add(model);
    }

    @Override
    public boolean remove(Car model) {
        for (Car car : this.models) {
            if (car.getModel().equals(model.getModel())) {
                this.models.remove(model);
                return true;
            }
        }
        return false;
    }

}
