package vehicles;

import people.Person;

/**
 * An interface to represent different kinds of Vehicles such as:
 * - cars
 * - motorcicles
 * - airplanes
 * - boats
 *
 * @author http://twitter.com/manoelcampos[Manoel Campos da Silva Filho]
 * @see https://en.wikipedia.org/wiki/Vehicle[Vehicles at Wikipedia]
 */
public interface Vehicle {
    /**
     * Gets the year the vehicle was manufactured
     * @return the manufacturing year
     */
    int getManufacturingYear();

    /**
     * Sets the year the vehicle was manufactured
     * @param year the year to set
     */
    void setManufacturingYear(int year);

    /**
     * Gets the {@link Person} that owns the vehicle.
     * @return the vehicle's owner
     */
    Person getOwner();

    /**
     * Sets the {@link Person} that owns the vehicle.
     * @param owner the vehicle's owner
     */
    void setOwner(Person owner);

    /**
     * Gets the vehicle age in number of years since it was manufactured.
     * @return the vehicle age in years
     */
    int getYears();
}
