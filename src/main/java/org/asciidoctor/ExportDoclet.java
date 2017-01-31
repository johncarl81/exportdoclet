/*
 * Copyright 2013-2015 John Ericksen
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

import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

/**
 * A {@link Doclet} that exports javadoc comments containing asciidoc text to asciidoc files,
 * instead of specific final formats such as HTML.
 *
 * @author John Ericksen
 * @see ExportRenderer
 */
public class ExportDoclet extends Doclet {

    /**
     * Holds the root of the program structure information.
     * From this root all other program structure information can be extracted.
     */
    private final RootDoc rootDoc;

    /**
     * Creates a ExportDoclet.
     *
     * @param rootDoc holds the root of the program structure information.
     *                From this root all other program structure information can be extracted.
     */
    public ExportDoclet(RootDoc rootDoc) {
        this.rootDoc = rootDoc;
    }

    /**
     * Method comment
     * @param options
     * @param errorReporter
     * @return
     */
    @SuppressWarnings("UnusedDeclaration")
    public static boolean validOptions(String[][] options, DocErrorReporter errorReporter) {
        return new StandardAdapter().validOptions(options, errorReporter);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static int optionLength(String option) {
        return new StandardAdapter().optionLength(option);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static boolean start(RootDoc rootDoc) {
        return new ExportDoclet(rootDoc).start();
    }

    boolean start() {
        return run();
    }

    /**
     * Private method comment
     * @return
     */
    private boolean run() {
        ExportRenderer renderer = new ExportRenderer();
        return renderer.render(rootDoc);
    }
}
