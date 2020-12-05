package easterRaces.entities.cars;

import easterRaces.common.ExceptionMessages;

public class MuscleCar extends BaseCar {

    public MuscleCar(String model, int horsePower) {
        super(model, setHorsePower(horsePower), 5000);
    }

    private static int setHorsePower(int horsePower) {
        if (horsePower < 400 || horsePower > 600) {
            throw new IllegalArgumentException(
                    String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
        }

        return horsePower;
    }

    //@Override
    //public void setHorsePower(int horsePower) {
    //    if (horsePower < 400 || horsePower > 600) {
    //      throw new IllegalArgumentException(
    //              String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
    //    }
    //    super.setHorsePower(horsePower);
    //}
}
