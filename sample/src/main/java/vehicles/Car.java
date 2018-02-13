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

/**
 * A Car represents a terrestrial link:Vehicle[]
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
