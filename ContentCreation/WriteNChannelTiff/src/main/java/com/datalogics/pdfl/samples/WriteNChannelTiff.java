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
 * This sample generates a multi-page TIFF file, selecting graphics drawn from
 * the first page of the PDF document provided.
 * 
 * Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved.
 *
 */

public class WriteNChannelTiff {
	 /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("WriteNChannelTiff Sample:");

        Library lib = new Library();

        try {
            System.out.println("Initialized the library.");
            String docPath = Library.getResourceDirectory() + "Sample_Input/sample.pdf";
            String outPath = "WriteNChannelTiff-out.tif";
            System.out.println("PDF Document to Images Sample:");

            if (args.length > 0 )
                docPath = args[0];
            if (args.length > 1 )
                outPath = args[1];

            System.out.println("Will read " + docPath + " and write to " + outPath);

            Document doc = new Document(docPath);
            Page pg = doc.getPage(0);

            // Get all inks tha are present on the page
            List<Ink> inks = pg.listInks();
            List<SeparationColorSpace> colorants = new ArrayList<SeparationColorSpace>();

            // Here we decide, which inks should be drawn
            for (Ink ink : inks) {
            	colorants.add(new SeparationColorSpace(pg, ink));
            }

            PageImageParams pip = new PageImageParams();
            pip.setHorizontalResolution(300.0);
            pip.setVerticalResolution(300.0);

            Image image = pg.getImage(pg.getCropBox(), pip, colorants);

            // Save images as multi-paged tiff - each page is a separated color from the page bitmap.
            image.save(outPath, ImageType.TIFF);
        }
        finally {
            lib.delete();
        }
    }
}
