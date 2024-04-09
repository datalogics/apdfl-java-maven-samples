package com.datalogics.pdfl.samples;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.datalogics.PDFL.Document;
import com.datalogics.PDFL.DrawFlags;
import com.datalogics.PDFL.ImageCollection;
import com.datalogics.PDFL.ImageType;
import com.datalogics.PDFL.Ink;
import com.datalogics.PDFL.Library;
import com.datalogics.PDFL.Page;
import com.datalogics.PDFL.PageImageParams;
import com.datalogics.PDFL.SeparationColorSpace;

/*
 * This sample demonstrates drawing a list of grayscale separations from a PDF file to multi-paged TIFF file.
 *
 * Copyright (c) 2007-2024, Datalogics, Inc. All rights reserved.
 *
 */
public class GetSeparatedImages {
	 /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("GetSeparatedImages Sample:");

        String sInput = Library.getResourceDirectory() + "Sample_Input/ducky.pdf";
        String sOutput = "GetSeparatedImages-out.tiff";
        if (args.length > 0)
            sInput = args[0];
        if (args.length > 1)
            sOutput = args[1];
        System.out.println("Input file: " + sInput + ", will write to " + sOutput);
        Library lib = new Library();

        try {

            Document doc = new Document(sInput);
            Page pg = doc.getPage(0);

            // Get all inks that are present on the page
            List<Ink> inks = pg.listInks();
            List<SeparationColorSpace> colorants = new ArrayList<SeparationColorSpace>();

            // Here we decide which inks should be drawn
            for (Ink ink : inks) {
            	colorants.add(new SeparationColorSpace(pg, ink));
            }

            PageImageParams pip = new PageImageParams();
            pip.setPageDrawFlags(EnumSet.of(DrawFlags.USE_ANNOT_FACES));
            pip.setHorizontalResolution(300.0);
            pip.setVerticalResolution(300.0);

            ImageCollection images = pg.getImageSeparations(pg.getCropBox(), pip, colorants);

            // Save images as multi-paged tiff - each page is a separated color from the page bitmap.
            images.save(sOutput, ImageType.TIFF);
        }
        finally {
            lib.delete();
        }
    }
}
