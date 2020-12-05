package easterRaces.core;


import easterRaces.common.ExceptionMessages;
import easterRaces.common.OutputMessages;
import easterRaces.core.interfaces.Controller;
import easterRaces.entities.cars.Car;
import easterRaces.entities.cars.MuscleCar;
import easterRaces.entities.cars.SportsCar;
import easterRaces.entities.drivers.Driver;
import easterRaces.entities.drivers.DriverImpl;
import easterRaces.entities.racers.Race;
import easterRaces.entities.racers.RaceImpl;
import easterRaces.repositories.interfaces.Repository;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {

    private Repository<Driver> riderRepository;
    private  Repository<Car> motorcycleRepository;
    private  Repository<Race> raceRepository;


    public ControllerImpl(Repository<Driver> riderRepository, Repository<Race> raceRepository, Repository<Car> motorcycleRepository) {
        this.riderRepository = riderRepository;
        this.raceRepository = raceRepository;
        this.motorcycleRepository = motorcycleRepository;
    }

    // public ControllerImpl() {
   //     this.riderRepository = new DriverRepository();
   //     this.raceRepository = new RaceRepository();
   //     this.motorcycleRepository = new CarRepository();
   // }
//
    @Override
    public String createDriver(String driver) {
        for (Driver driver1 : this.riderRepository.getAll()) {
            if (driver1.getName().equals(driver)) {
                return String.format(ExceptionMessages.DRIVER_EXISTS, driver);
            }
        }
        this.riderRepository.add(new DriverImpl(driver));
        return String.format(OutputMessages.DRIVER_CREATED, driver);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
        for (Car car : motorcycleRepository.getAll()) {
            if (car.getModel().equals(model)) {
                return String.format(ExceptionMessages.CAR_EXISTS, model);
            }
        }

        if (type.equals("Muscle")) {
            motorcycleRepository.add(new MuscleCar(model, horsePower));
            return String.format(OutputMessages.CAR_CREATED, "MuscleCar", model);
        } else if (type.equals("Sports")) {
            motorcycleRepository.add(new SportsCar(model, horsePower));
            return String.format(OutputMessages.CAR_CREATED, "SportsCar", model);
        }
        return null;
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {
        if (this.motorcycleRepository.getAll()
                .stream()
                .noneMatch(e -> e.getModel().equals(carModel))) {
            return String.format(ExceptionMessages.CAR_NOT_FOUND, carModel);
        }

        if (driverNotFound(driverName)) {
            return String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName);
        }

        Car car = this.motorcycleRepository.getAll()
                .stream()
                .filter(e -> e.getModel()
                        .equals(carModel))
                .findAny()
                .get();

        Driver driver = this.riderRepository.getAll()
                .stream()
                .filter(e -> e.getName().equals(driverName))
                .findAny()
                .get();
        driver.addCar(car);
        return String.format(OutputMessages.CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {
        if (this.raceRepository.getAll()
                .stream()
                .noneMatch(e -> e.getName().equals(raceName))) {
            return String.format(ExceptionMessages.RACE_NOT_FOUND, raceName);
        }
        if (driverNotFound(driverName)) {
            return String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName);
        }

        for (Driver driver : riderRepository.getAll()) {
            if (driver.getCar() == null) {
                return String.format(ExceptionMessages.DRIVER_NOT_PARTICIPATE, driverName);
            }
        }

        Driver driver = this.riderRepository.getAll()
                .stream()
                .filter(e -> e.getName().equals(driverName))
                .findAny()
                .get();

        Race race = this.raceRepository.getAll()
                .stream()
                .filter(e -> e.getName().equals(raceName))
                .findAny()
                .get();

        race.addDriver(driver);
        return String.format(OutputMessages.DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {
        if (this.raceRepository.getAll()
                .stream()
                .noneMatch(e -> e.getName().equals(raceName))) {
            return String.format(ExceptionMessages.RACE_NOT_FOUND, raceName);
        }
        if (this.raceRepository.getByName(raceName).getDrivers().size() < 3) {
            return String.format(ExceptionMessages.RACE_INVALID, raceName, 3);
        }

        List<Driver> driverList = null;
        Race currRace = this.raceRepository.getByName(raceName);

        driverList = currRace.getDrivers()
                .stream()
                .sorted((a, b) ->
                        Double.compare(b.getCar().calculateRacePoints(currRace.getLaps()),
                                a.getCar().calculateRacePoints(currRace.getLaps())))
                .limit(3)
                .collect(Collectors.toList());

        StringBuilder result = new StringBuilder();
        return result.append(String.format(OutputMessages.DRIVER_FIRST_POSITION,
                driverList.get(0).getName(), raceName))
                .append(System.lineSeparator())
                .append(String.format(OutputMessages.DRIVER_SECOND_POSITION,
                        driverList.get(1).getName(), raceName))
                .append(System.lineSeparator())
                .append(String.format(OutputMessages.DRIVER_THIRD_POSITION,
                        driverList.get(2).getName(), raceName))
                .append(System.lineSeparator()).toString().trim();
    }

    @Override
    public String createRace(String name, int laps) {
        if (this.raceRepository.getAll()
                .stream()
                .anyMatch(e -> e.getName().equals(name))) {
            return String.format(ExceptionMessages.RACE_EXISTS, name);
        }
        this.raceRepository.add(new RaceImpl(name, laps));
        return String.format(OutputMessages.RACE_CREATED, name);
    }

    private boolean driverNotFound(String driverName) {
        if (this.riderRepository.getAll()
                .stream()
                .noneMatch(e -> e.getName().equals(driverName))) {
            return true;
        }
        return false;
    }
}
