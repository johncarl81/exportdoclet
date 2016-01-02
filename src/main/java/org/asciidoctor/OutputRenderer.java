package org.asciidoctor;

import com.sun.javadoc.Doc;
import org.asciidoctor.outputdoclet.DocletRenderer;

/**
 * @author John Ericksen
 */
public class OutputRenderer implements DocletRenderer{
    @Override
    public void renderDoc(Doc doc) {
        System.out.println("name: " + doc.name());
        if(doc.position() != null) {
            System.out.println("Position: " + doc.position().file().getName());
        }
        System.out.println("contents: " + doc.commentText());
    }
}
