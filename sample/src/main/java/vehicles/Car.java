package vehicles;

/**
 * A Car represents a terrestrial {@link Vehicle}
 * with 4 wheels.
 *
 * @author http://twitter.com/manoelcampos[Manoel Campos da Silva Filho]
 */
public class Car extends AbstractVehicle {
    /**
     * An enumeration to represent left and right sides.
     */
    enum Side {LEFT, RIGHT}

    private Side driverSide;

    /**
     * Gets the side where the driver seats.
     * @return the driver's seat side
     */
    public Side getDriverSide() {
        return driverSide;
    }

    /**
     * Sets the side where the driver seats.
     * @param driverSide the driver's seat side to set
     */
    public void setDriverSide(Side driverSide) {
        this.driverSide = driverSide;
    }
}
