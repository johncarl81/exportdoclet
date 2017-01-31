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
 * Class level comment
 */
public class ExportDoclet extends Doclet {

    /**
     * Inner class commend
     */
    public static class InnerClass {

        /**
         * Inner class constructor
         */
        public InnerClass(){

        }

        /**
         * Inner class method
         */
        public void run(){}
    }

    /**
     * Field comment
     */
    private final RootDoc rootDoc;

    /**
     * Constructor comment
     * @param rootDoc
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
