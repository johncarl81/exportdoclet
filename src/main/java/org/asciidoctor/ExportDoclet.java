/*
 * Copyright 2013-2018 John Ericksen
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
package org.asciidoctor;

import jdk.javadoc.doclet.*;
import jdk.javadoc.doclet.Doclet;

import javax.lang.model.*;
import java.util.*;

/**
 * A link:Doclet[] that exports javadoc comments containing AsciiDoc text to AsciiDoc files,
 * instead of specific final formats such as HTML.
 *
 * @author John Ericksen
 * @see ExportRenderer
 */
public class ExportDoclet implements Doclet {

    @Override
    public void init(Locale locale, Reporter reporter) {
    }

    @Override
    public String getName() {
        return "ExportDoclet";
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        return Collections.emptySet();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        return new ExportRenderer(environment).render();
    }
}
