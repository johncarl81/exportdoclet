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
     * Gets the link:Person[] that owns the vehicle.
     * @return the vehicle's owner
     */
    Person getOwner();

    /**
     * Sets the link:Person[] who owns the vehicle.
     * @param owner the vehicle's owner
     */
    void setOwner(Person owner);

    /**
     * Gets the vehicle age in number of years since it was manufactured.
     * @return the vehicle age in years
     */
    int getYears();
}
