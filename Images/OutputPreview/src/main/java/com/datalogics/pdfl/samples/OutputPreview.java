package com.datalogics.pdfl.samples;

import java.util.ArrayList;
import java.util.List;

import com.datalogics.PDFL.Document;
import com.datalogics.PDFL.Image;
import com.datalogics.PDFL.ImageType;
import com.datalogics.PDFL.Ink;
import com.datalogics.PDFL.Library;
import com.datalogics.PDFL.Page;
import com.datalogics.PDFL.PageImageParams;
import com.datalogics.PDFL.SeparationColorSpace;

/*
 * This sample demonstrates creating an Output Preview Image which is used during Soft Proofing prior to printing to visualize combining different Colorants.
 *
 * 
 * Copyright (c)2023-2024, Datalogics, Inc. All rights reserved.
 *
 */

public class OutputPreview {
    public static String CreateOutputFileName(ArrayList<String> colorants) {
        String outputFileName = "OutputPreview_";

        for (String colorant : colorants) {
            outputFileName += colorant + "_";
        }

        outputFileName += ".tiff";

        return outputFileName;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("OutputPreview Sample:");

        String sInput = Library.getResourceDirectory() + "Sample_Input/spotcolors1.pdf";

        // Here you specify the Colorant names of interest
        ArrayList<String> colorantsToUse = new ArrayList<String>();
        colorantsToUse.add("Yellow");
        colorantsToUse.add("Black");

        ArrayList<String> colorantsToUse2 = new ArrayList<String>();
        colorantsToUse2.add("PANTONE 554 CVC");
        colorantsToUse2.add("PANTONE 814 2X CVC");
        colorantsToUse2.add("PANTONE 185 2X CVC");

        Library lib = new Library();

        try {
            Document doc = new Document(sInput);
            Page pg = doc.getPage(0);

            // Get all inks that are present on the page
            List<Ink> inks = pg.listInks();

            List<SeparationColorSpace> colorants = new ArrayList<SeparationColorSpace>();

            for (Ink ink : inks) {
                for (String theColorant : colorantsToUse) {
                    if (ink.getColorantName().equals(theColorant))
                    {
                        colorants.add(new SeparationColorSpace(pg, ink));
                    }
                }
            }

            List<SeparationColorSpace> colorants2 = new ArrayList<SeparationColorSpace>();

            for (Ink ink : inks) {
                for (String theColorant : colorantsToUse2) {
                    if (ink.getColorantName().equals(theColorant))
                    {
                        colorants2.add(new SeparationColorSpace(pg, ink));
                    }
                }
            }

            PageImageParams pip = new PageImageParams();
            pip.setPageDrawFlags(EnumSet.of(DrawFlags.USE_ANNOT_FACES));
            pip.setHorizontalResolution(300.0);
            pip.setVerticalResolution(300.0);

            // Create Output Preview images using the Specified Colorants
            Image image = pg.getOutputPreviewImage(pg.getCropBox(), pip, colorants);

            image.save(String.format(CreateOutputFileName(colorantsToUse)), ImageType.TIFF);

            Image image2 = pg.getOutputPreviewImage(pg.getCropBox(), pip, colorants2);

            image2.save(String.format(CreateOutputFileName(colorantsToUse2)), ImageType.TIFF);

            pg.delete();
            doc.delete();
        }
        finally {
            lib.delete();
        }
    }
}
