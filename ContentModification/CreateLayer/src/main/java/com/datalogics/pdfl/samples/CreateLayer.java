/*
 * This sample adds Optional Content Groups (layers) to a PDF document and
 * then adds Content to those layers.
 * 
 * The related ChangeLayerConfiguration program makes layers visible or invisible.
 * 
 * You can toggle back and forth to make a layer visible or invisible
 * in a PDF Viewer.
 *
 * Copyright (c) 2007-2025, Datalogics, Inc. All rights reserved.
 *
 */

package com.datalogics.pdfl.samples;

import com.datalogics.PDFL.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

/**
 *
 */
public class CreateLayer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("CreateLayer Sample:");

        Library lib = new Library();

        try {
            System.out.println("Initialized the library.");
            String sInput = Library.getResourceDirectory() + "Sample_Input/ducky.pdf";
            String sOutput = "CreateLayer-out.pdf";
            if (args.length > 0)
                sInput = args[0];
            if (args.length > 1)
                sOutput = args[1];
            System.out.println("Input file: " + sInput + ", will write to " + sOutput);

            Document doc = new Document(sInput);

            Page pg = doc.getPage(0);
            Image image = (Image)pg.getContent().getElement(0);
            image.setMatrix(new Matrix(image.getMatrix().getA() * .5, 0, 0, image.getMatrix().getD() * .5, image.getMatrix().getH(), image.getMatrix().getV()));

            Image image2 = new Image(Library.getResourceDirectory() + "Sample_Input/Image.png");

            Text text = new Text();
            Matrix matrix = new Matrix();
            Font font = new Font("Helvetica");
            GraphicState graphicState = new GraphicState();
            TextState textState = new TextState();

            matrix.setA(42);
            matrix.setD(22);
            matrix.setH(72);
            matrix.setV(72);

            TextRun textRun = new TextRun("sample text", font, graphicState, textState, matrix);
            text.addRun(textRun);

            Text text2 = new Text();

            matrix.setA(30);
            matrix.setD(30);
            matrix.setH(72);
            matrix.setV(288);

            TextRun textRun2 = new TextRun("Text definition provided here", font, graphicState, textState, matrix);
            text2.addRun(textRun2);

            // Containers, Forms and Annotations can be attached to an
            // OptionalContentGroup; other content (like Image) can
            // be made optional by placing it inside a Container
            Container imageContainer = new Container();
            imageContainer.setContent(new Content());
            imageContainer.getContent().addElement(image);

            Container imageContainer2 = new Container();
            imageContainer2.setContent(new Content());
            imageContainer2.getContent().addElement(image2);

            Container textContainer = new Container();
            textContainer.setContent(new Content());
            textContainer.getContent().addElement(text);

            Container textContainer2 = new Container();
            textContainer2.setContent(new Content());
            textContainer2.getContent().addElement(text2);

            Document newDoc = new Document();

            Page newPage = newDoc.createPage(Document.BEFORE_FIRST_PAGE, pg.getMediaBox());

            newPage.getContent().addElement(imageContainer);
            newPage.getContent().addElement(imageContainer2);
            newPage.getContent().addElement(textContainer);
            newPage.getContent().addElement(textContainer2);

            // We create new OptionalContentGroups and place them in the OptionalContentConfig.Order array
            ArrayList<OptionalContentGroup> ocgs = createNewOptionalContentGroups(newDoc, new ArrayList<String>( Arrays.asList("Rubber Ducky", "PNG Logo", "Example Text", "Text Definition")));

            associateOCGWithContainer(newDoc, ocgs.get(0), imageContainer);
            associateOCGWithContainer(newDoc, ocgs.get(1), imageContainer2);
            associateOCGWithContainer(newDoc, ocgs.get(2), textContainer);
            associateOCGWithContainer(newDoc, ocgs.get(3), textContainer2);

            newPage.updateContent();

            newDoc.save(EnumSet.of(SaveFlags.FULL), sOutput);
        }
        finally {
            lib.delete();
        }
    }

    public static ArrayList<OptionalContentGroup> createNewOptionalContentGroups(Document doc, ArrayList<String> names)
    {
        // Create an OptionalContentGroup
        ArrayList<OptionalContentGroup> ocgs = new ArrayList<OptionalContentGroup>();

        OptionalContentGroup ocg = new OptionalContentGroup(doc, names.get(0));
        OptionalContentGroup ocg2 = new OptionalContentGroup(doc, names.get(1));
        OptionalContentGroup ocg3 = new OptionalContentGroup(doc, names.get(2));
        OptionalContentGroup ocg4 = new OptionalContentGroup(doc, names.get(3));

        ocgs.add(ocg);
        ocgs.add(ocg2);
        ocgs.add(ocg3);
        ocgs.add(ocg4);

        // Add it to the Order array -- this is required so that it will appear in the 'Layers' panel in a PDF Viewer.
        OptionalContentOrderArray order_list = doc.getDefaultOptionalContentConfig().getOrder();

        OptionalContentOrderArray grouping = new OptionalContentOrderArray(doc, "Image Grouping");
        grouping.add(new OptionalContentOrderLeaf(ocg));
        grouping.add(new OptionalContentOrderLeaf(ocg2));

        OptionalContentOrderArray grouping2 = new OptionalContentOrderArray(doc, "Text Grouping");
        grouping2.add(new OptionalContentOrderLeaf(ocg3));
        grouping2.add(new OptionalContentOrderLeaf(ocg4));

        order_list.insert(order_list.getLength(), grouping);
        order_list.insert(order_list.getLength(), grouping2);

        return ocgs;
    }

    // Associate a Container with an OptionalContentGroup via an OptionalContentMembershipDict.
    // This function associates a Containter with a single OptionalContentGroup and uses
    // a VisibilityPolicy of AnyOn.
    public static void associateOCGWithContainer(Document doc, OptionalContentGroup ocg, Container cont)
    {
        // Create an OptionalContentMembershipDict.  The options here are appropriate for a
        // 'typical' usage; other options can be used to create an 'inverting' layer
        // (i.e. 'Display this content when the layer is turned OFF'), or to make the
        // Container's visibility depend on several OptionalContentGroups
        OptionalContentMembershipDict ocmd = new OptionalContentMembershipDict(doc,
                new ArrayList<OptionalContentGroup>(Arrays.asList(ocg)), VisibilityPolicy.ANY_ON);

        cont.setOptionalContentMembershipDict(ocmd);
    }

}
