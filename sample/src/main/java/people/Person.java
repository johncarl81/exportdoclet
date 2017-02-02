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
package people;

/**
 * An interface to represent different kinds of People.
 *
 * @author http://twitter.com/manoelcampos[Manoel Campos da Silva Filho]
 * @see https://en.wikipedia.org/wiki/People[People at Wikipedia]
 */
public interface Person {
    /**
     * Gets the person's name.
     * @return the name of the person
     */
    String getName();
}
