package vehicles;

import people.Person;

import java.time.LocalDate;

/**
 * An abstract class for implementing different kinds of {@link Vehicle}.
 *
 * @author http://twitter.com/manoelcampos[Manoel Campos da Silva Filho]
 */
public class AbstractVehicle implements Vehicle {
    private int manufacturingYear;
    private Person owner;

    @Override
    public int getManufacturingYear() {
        return manufacturingYear;
    }

    @Override
    public void setManufacturingYear(int year) {
        this.manufacturingYear = year;
    }

    @Override
    public Person getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public int getYears() {
        return LocalDate.now().minusYears(manufacturingYear).getYear();
    }
}
