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
package org.asciidoctor;

import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MemberDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
    private final RootDoc rootDoc;

    public ExportRenderer(RootDoc rootDoc){
        this.rootDoc = rootDoc;
    }

    /**
     * Renders classes and packages javadocs, inside a link:RootDoc[] object, to AsciiDoc files.
     *
     * @return true if successful, false otherwise
     */
    public boolean render() {
        Set<PackageDoc> packages = new HashSet<PackageDoc>();
        for (ClassDoc doc : rootDoc.classes()) {
            packages.add(doc.containingPackage());
            renderClass(doc);
        }

        for (PackageDoc doc : packages) {
            renderPackage(doc);
            //renderer.renderDoc(doc);
        }

        return true;
    }

    /**
     * Renders a class documentation to an AsciiDoc file.
     *
     * @param doc the class documentation object
     */
    private void renderClass(ClassDoc doc) {
        try {
            PrintWriter writer = getWriter(doc.containingPackage(), doc.name());
            if (doc.position() != null) {
                outputText(doc.name(), doc.getRawCommentText(), writer);
            }
            for (MemberDoc member : doc.fields(false)) {
                outputText(member.name(), member.getRawCommentText(), writer);
            }
            for (MemberDoc member : doc.constructors(false)) {
                outputText(member.name(), member.getRawCommentText(), writer);
            }
            for (MemberDoc member : doc.methods(false)) {
                outputText(member.name(), member.getRawCommentText(), writer);
            }
            for (MemberDoc member : doc.enumConstants()) {
                outputText(member.name(), member.getRawCommentText(), writer);
            }
            if (doc instanceof AnnotationTypeDoc) {
                for (MemberDoc member : ((AnnotationTypeDoc) doc).elements()) {
                    outputText(member.name(), member.getRawCommentText(), writer);
                }
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders a package documentation to an AsciiDoc file.
     *
     * @param doc the package documentation object
     */
    private void renderPackage(PackageDoc doc){
        try {
            PrintWriter writer = getWriter(doc, "package-info");
            writer.println(doc.name());
            if (doc.position() != null) {
                outputText(doc.name(), doc.getRawCommentText(), writer);
            }
            if (doc instanceof AnnotationTypeDoc) {
                for (MemberDoc member : ((AnnotationTypeDoc) doc).elements()) {
                    outputText(member.name(), member.getRawCommentText(), writer);
                }
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        writer.println(cleanJavadocInput(comment));
        writer.println("// end::" + tag + "[]");
    }

    private String cleanJavadocInput(String input) {
        return input.trim()
                .replaceAll("\n ", "\n") // Newline space to accommodate javadoc newlines.
                .replaceAll("(?m)^( *)\\*\\\\/$", "$1*/"); // Multi-line comment end tag is translated into */.
    }

    /**
     * Gets a link:PrintWriter[] to export the documentation of a class or package
     * to an AsciiDoc file.
     *
     * @param packageDoc the package documentation object that will be the package that the documentation
     *                   is being exported or the package of the class that its documentation
     *                   is being exported
     * @param name the name of the AsciiDoc file to export the documentation to
     */
    private PrintWriter getWriter(PackageDoc packageDoc, String name) throws FileNotFoundException {
        File packageDirectory = new File(getOutputDir() + packageDoc.name().replace('.', File.separatorChar));
        if(!packageDirectory.exists() && !packageDirectory.mkdirs()){
            throw new RuntimeException("The directory was not created due to unknown reason.");
        }

        File file = new File(packageDirectory, name + ".adoc");
        return new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
    }

    /**
     * Gets the output directory passed as a command line argument to javadoc tool.
     * @return the output directory to export the javadocs
     */
    private String getOutputDir() {
        for (String[] option : rootDoc.options()) {
            if(option.length == 2 && option[0].equals("-d")) {
                return includeTrailingDirSeparator(option[1]);
            }
        }

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

}
