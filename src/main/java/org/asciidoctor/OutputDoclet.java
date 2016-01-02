/**
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
import org.asciidoctor.outputdoclet.DocletIterator;
import org.asciidoctor.outputdoclet.StandardAdapter;


public class OutputDoclet extends Doclet {

    private final RootDoc rootDoc;
    private final DocletIterator iterator;

    public OutputDoclet(RootDoc rootDoc) {
        this.rootDoc = rootDoc;
        this.iterator = new DocletIterator();
    }

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
        return new OutputDoclet(rootDoc).start(new StandardAdapter());
    }

    boolean start(StandardAdapter standardDoclet) {
        return run(standardDoclet);
    }

    private boolean run(StandardAdapter standardDoclet) {
        OutputRenderer renderer = new OutputRenderer();
        return iterator.render(rootDoc, renderer) &&
                standardDoclet.start(rootDoc);
    }
}
