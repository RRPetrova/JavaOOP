package easterRaces.repositories;

import easterRaces.common.ExceptionMessages;
import easterRaces.entities.racers.Race;
import easterRaces.repositories.interfaces.Repository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RaceRepository<E> implements Repository<Race> {

    private Collection<Race> models;

    public RaceRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public Race getByName(String name) {
        for (Race race : this.models) {
            if (race.getName().equals(name)) {
                return race;
            }
        }
      throw new IllegalArgumentException(String.format
              (ExceptionMessages.RACE_NOT_FOUND, name));
    }

    @Override
    public Collection<Race> getAll() {
        return Collections.unmodifiableCollection(this.models);
    }

    @Override
    public void add(Race model) {
        this.models.add(model);
    }

    @Override
    public boolean remove(Race model) {
        for (Race race : this.models) {
            if (race.getName().equals(model.getName())) {
                this.models.remove(model);
                return true;
            }
        }
        return false;
    }
}
