package easterRaces.entities.cars;

import easterRaces.common.ExceptionMessages;

public class SportsCar extends BaseCar {
    public SportsCar(String model, int horsePower) {
        super(model, setHorsePower(horsePower), 3000);
    }

    private static int setHorsePower(int horsePower) {
        if (horsePower < 250 || horsePower > 450) {
                  throw new IllegalArgumentException(
                          String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
              }
        return horsePower;
    }

}
