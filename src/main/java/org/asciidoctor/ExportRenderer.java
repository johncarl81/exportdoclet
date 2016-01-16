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
import java.util.HashSet;
import java.util.Set;

/**
 * @author John Ericksen
 */
public class ExportRenderer {

    public boolean render(RootDoc rootDoc) {
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

    public void renderClass(ClassDoc doc) {
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

    public void renderPackage(PackageDoc doc){
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

    private void outputText(String tag, String comment, PrintWriter writer) {
        writer.println("// tag::" + tag + "[]");
        writer.println(cleanJavadocInput(comment));
        writer.println("// end::" + tag + "[]");
    }

    protected String cleanJavadocInput(String input) {
        return input.trim()
                .replaceAll("\n ", "\n") // Newline space to accommodate javadoc newlines.
                .replaceAll("(?m)^( *)\\*\\\\/$", "$1*/"); // Multi-line comment end tag is translated into */.
    }

    private PrintWriter getWriter(PackageDoc packageDoc, String name) throws FileNotFoundException {
        File pacakgeDirectory = new File(packageDoc.name().replace('.', File.separatorChar));
        if(!pacakgeDirectory.exists()){
            pacakgeDirectory.mkdirs();
        }
        File file = new File(pacakgeDirectory, name + ".adoc");
        return new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
    }
}
