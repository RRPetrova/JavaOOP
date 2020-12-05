package easterRaces.repositories;

import easterRaces.common.ExceptionMessages;
import easterRaces.entities.drivers.Driver;
import easterRaces.repositories.interfaces.Repository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DriverRepository<E> implements Repository<Driver> {

    private Collection<Driver> models;

    public DriverRepository() {
        models = new ArrayList<>();
    }

    @Override
    public Driver getByName(String name) {
        for (Driver driver : this.models) {
            if (driver.getName().equals(name)) {
                return driver;
            }
        }
        throw new IllegalArgumentException
                (String.format(ExceptionMessages.DRIVER_NOT_FOUND, name));
    }

    @Override
    public Collection<Driver> getAll() {
        return Collections.unmodifiableCollection(this.models);
    }

    @Override
    public void add(Driver model) {
        this.models.add(model);
    }

    @Override
    public boolean remove(Driver model) {
        for (Driver driver : this.models) {
            if (driver.getName().equals(model.getName())) {
                this.models.remove(model);
                return true;
            }
        }
        return false;
    }
}
