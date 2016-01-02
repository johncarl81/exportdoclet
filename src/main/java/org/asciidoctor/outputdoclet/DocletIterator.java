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
package org.asciidoctor.outputdoclet;

import com.sun.javadoc.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Iterates over the various elements of a RootDoc, handing off to the DocletRenderer to perform the rendering work.
 *
 * @author John Ericksen
 */
public class DocletIterator {

    /**
     * Renders a RootDoc's contents.
     *
     * @param rootDoc
     * @param renderer
     */
    public boolean render(RootDoc rootDoc, DocletRenderer renderer) {
        Set<PackageDoc> packages = new HashSet<PackageDoc>();
        for (ClassDoc doc : rootDoc.classes()) {
            packages.add(doc.containingPackage());
            renderClass(doc, renderer);
        }
        for (PackageDoc doc : packages) {
            renderer.renderDoc(doc);
        }
        return true;
    }

    /**
     * Renders an individual class.
     *
     * @param doc input
     */
    private void renderClass(ClassDoc doc, DocletRenderer renderer) {
        //handle the various parts of the Class doc
        renderer.renderDoc(doc);
        for ( MemberDoc member : doc.fields() ) {
            renderer.renderDoc(member);
        }
        for ( MemberDoc member : doc.constructors() ) {
            renderer.renderDoc(member);
        }
        for ( MemberDoc member : doc.methods() ) {
            renderer.renderDoc(member);
        }
        for ( MemberDoc member : doc.enumConstants() ) {
            renderer.renderDoc(member);
        }
        if ( doc instanceof AnnotationTypeDoc) {
            for ( MemberDoc member : ((AnnotationTypeDoc)doc).elements() ) {
                renderer.renderDoc(member);
            }
        }
    }
}
