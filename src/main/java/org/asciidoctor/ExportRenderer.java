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

import javax.lang.model.element.*;
import javax.lang.model.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * A renderer class that actually exports javadoc comments containing AsciiDoc text to AsciiDoc files,
 * instead of specific final formats such as HTML.
 * It is used when the link:ExportDoclet[] is started.
 *
 * @author John Ericksen
 */
public class ExportRenderer {
    /**
     * Holds the root of the program structure information.
     * From this root all other program structure information can be extracted.
     */
    private final DocletEnvironment rootDoc;
    private final boolean includeCaptions;

    public ExportRenderer(DocletEnvironment rootDoc){
        this(rootDoc, false);
    }

    public ExportRenderer(DocletEnvironment rootDoc, boolean includeCaptions){
        this.rootDoc = rootDoc;
        this.includeCaptions = includeCaptions;
    }

    /**
     * Renders classes and packages javadocs, inside a link:RootDoc[] object, to AsciiDoc files.
     *
     * @return true if successful, false otherwise
     */
    public boolean render() {
        renderRootElements(rootDoc.getSpecifiedElements());
        return true;
    }

    private void renderRootElements(Set<? extends Element> elements) {
        for(Element typeElement : ElementFilter.typesIn(elements)) {
            renderType(typeElement);
        }
        for(Element packageElement : ElementFilter.packagesIn(elements)) {
            renderPackage(packageElement);
        }
    }

    /**
     * Renders a class documentation to an AsciiDoc file.
     *
     * @param element the class documentation object
     */
    private void renderType(Element element) {
        try (PrintWriter writer = getWriter(getPackageName(element), element.getSimpleName().toString())) {
            renderEnclosedElements(element, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders a package documentation to an AsciiDoc file.
     *
     * @param packageElement the package documentation object
     */
    private void renderPackage(Element packageElement){
        try (PrintWriter writer = getWriter(getPackageName(packageElement), "package-info")) {
            renderEnclosedElements(packageElement, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void renderEnclosedElements(Element rootElement, PrintWriter writer) {
        outputText(rootElement, writer);

        renderRootElements(new HashSet<>(rootElement.getEnclosedElements()));

        Set<Element> subElements = new HashSet<>(rootElement.getEnclosedElements());
        subElements.removeAll(ElementFilter.typesIn(rootElement.getEnclosedElements()));
        subElements.removeAll(ElementFilter.packagesIn(rootElement.getEnclosedElements()));

        for(Element subElement : subElements) {
            outputText(subElement, writer);
        }

        writer.flush();
    }

    private void outputText(List<? extends Element> elements, PrintWriter writer) {
        for (Element element : elements) {
            outputText(element.getSimpleName().toString(), rootDoc.getElementUtils().getDocComment(element), writer);
        }
    }

    private void outputText(Element element, PrintWriter writer) {
        outputText(element.getSimpleName().toString(), rootDoc.getElementUtils().getDocComment(element), writer);
    }

    /**
     * Exports a javadoc comment using a given link:PrintWriter[], surrounding
     * it by a AsciiDoc tag with a specific name.
     *
     * @param tag the name of the tag to surround the javadoc comment into the AsciiDoc file
     * @param comment the javadoc comment to export
     * @param writer the link:PrintWriter[] to be used to export the javadoc comment to an AsciiDoc file
     */
    private void outputText(String tag, String comment, PrintWriter writer) {
        writer.println("// tag::" + tag + "[]");
        if (includeCaptions) {
            writer.println(String.format("== %s", tag));
        }
        writer.println(cleanJavadocInput(comment));
        writer.println("// end::" + tag + "[]");
    }

    private String cleanJavadocInput(String input) {
        if(input == null) {
            return "";
        }
        return input.trim()
                .replaceAll("\n ", "\n") // Newline space to accommodate javadoc newlines.
                .replaceAll("(?m)^( *)\\*\\\\/$", "$1*/"); // Multi-line comment end tag is translated into */.
    }

    /**
     * Gets a link:PrintWriter[] to export the documentation of a class or package
     * to an AsciiDoc file.
     *
     * @param packageName the package documentation object that will be the package that the documentation
     *                   is being exported or the package of the class that its documentation
     *                   is being exported
     * @param name the name of the AsciiDoc file to export the documentation to
     */
    private PrintWriter getWriter(String packageName, String name) throws FileNotFoundException {
        File packageDirectory = new File(getOutputDir() + packageName.replace('.', File.separatorChar));
        if(!packageDirectory.exists() && !packageDirectory.mkdirs()){
            throw new RuntimeException("The directory was not created due to unknown reason.");
        }

        File file = new File(packageDirectory, name + ".adoc");
        return new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
    }

    private String getPackageName(Element element) {
        return this.rootDoc.getElementUtils().getPackageOf(element).toString();
    }

    /**
     * Gets the output directory passed as a command line argument to javadoc tool.
     * @return the output directory to export the javadocs
     */
    private String getOutputDir() {
        /*TODO: for (String[] option : rootDoc.options()) {
            if(option.length == 2 && option[0].equals("-d")) {
                return includeTrailingDirSeparator(option[1]);
            }
        }*/

        return "";
    }

    /**
     * Adds a trailing slash at the end of a path if it doesn't have one yet.
     * The trailing slash type is system-dependent and will be accordingly selected.
     *
     * @param path the path to include a trailing slash
     * @return the path with a trailing slash if there wasn't one and the path is not empty,
     * the original path otherwise
     */
    private String includeTrailingDirSeparator(String path){
        if(path.trim().isEmpty()) {
            return path;
        }

        if(path.charAt(path.length()-1) != File.separatorChar){
            return path + File.separator;
        }

        return path;
    }


    public static final class Test{

        /**
         * Something
         */
        public void test() {
            
        }
    }
}
