/*
 * Copyright 2013-2016 John Ericksen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vehicles;

import people.Person;

import java.time.LocalDate;

/**
 * An abstract class for implementing different kinds of link:Vehicle[].
 *
 * @author http://twitter.com/manoelcampos[Manoel Campos da Silva Filho]
 */
public class AbstractVehicle implements Vehicle {
    /**
     * The year the vehicle was manufactured.
     */
    private int manufacturingYear;

    /**
     * The link:Person[] who owns the vehicle.
     */
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
