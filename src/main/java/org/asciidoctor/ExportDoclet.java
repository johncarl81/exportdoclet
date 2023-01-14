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

    private boolean includeCaptions = false;

    @Override
    public Set<? extends Option> getSupportedOptions() {
        Option[] options = {
                new Option() {
                    private final List<String> someOption = Arrays.asList(
                            "--captions",
                            "-cp"
                    );

                    @Override
                    public int getArgumentCount() {
                        return 1;
                    }

                    @Override
                    public String getDescription() {
                        return "include captions in output";
                    }

                    @Override
                    public Option.Kind getKind() {
                        return Kind.STANDARD;
                    }

                    @Override
                    public List<String> getNames() {
                        return someOption;
                    }

                    @Override
                    public String getParameters() {
                        return "boolean";
                    }

                    @Override
                    public boolean process(String opt, List<String> arguments) {
                        includeCaptions = arguments.get(0).equalsIgnoreCase("true");
                        return true;
                    }
                }
        };
        return new HashSet<>(Arrays.asList(options));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        return new ExportRenderer(environment, includeCaptions).render();
    }
}
