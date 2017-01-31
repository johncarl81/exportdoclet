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
     * Creates a ExportDoclet to export javadoc comments to asciidoc files.
     *
     * @param rootDoc holds the root of the program structure information.
     *                From this root all other program structure information can be extracted.
     */
    public ExportDoclet(RootDoc rootDoc) {
        this.rootDoc = rootDoc;
    }

    /**
     * Validates command line options.
     *
     * @param options the array of given options
     * @param errorReporter an object that allows printing error messages for invalid options
     * @return true if the options are valid, false otherwise
     * @see Doclet#validOptions(String[][], DocErrorReporter)
     */
    @SuppressWarnings("UnusedDeclaration")
    public static boolean validOptions(String[][] options, DocErrorReporter errorReporter) {
        return new StandardAdapter().validOptions(options, errorReporter);
    }

    /**
     * Gets the number of arguments that a given command line option must contain.
     * @param option the command line option
     * @return the number of arguments required for the given option
     * @see Doclet#optionLength(String)
     */
    @SuppressWarnings("UnusedDeclaration")
    public static int optionLength(String option) {
        return new StandardAdapter().optionLength(option);
    }

    /**
     * Return the version of the Java Programming Language supported
     * by this doclet.
     * @return the Java language supported version
     * @see Doclet#languageVersion()
     */
    @SuppressWarnings("UnusedDeclaration")
    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }

    /**
     * Starts the doclet.
     * @param rootDoc the root of the program structure information.
     *                From this root all other program structure information can be extracted.
     * @return true if the doclet was started successfuly, false otherwise
     * @see Doclet#start(RootDoc)
     */
    @SuppressWarnings("UnusedDeclaration")
    public static boolean start(RootDoc rootDoc) {
        return new ExportDoclet(rootDoc).start();
    }

    boolean start() {
        ExportRenderer renderer = new ExportRenderer();
        return renderer.render(rootDoc);
    }
}
