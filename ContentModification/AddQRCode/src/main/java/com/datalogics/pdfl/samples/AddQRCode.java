package com.datalogics.pdfl.samples.ContentModification.AddQRCode;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.datalogics.PDFL.Document;
import com.datalogics.PDFL.Library;
import com.datalogics.PDFL.Page;
import com.datalogics.PDFL.SaveFlags;

/*
 *
 * This sample shows how to add a QR barcode to a PDF page
 *
 * Copyright (c) 2024, Datalogics, Inc. All rights reserved.
 *
 */

public class AddQRCode {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
        System.out.println("AddQRCode sample:");

        String sInput = Library.getResourceDirectory() + "Sample_Input/sample_links.pdf";
        String sOutput = "AddQRCode-out.pdf";

        Library lib = new Library();

        try {
            if (args.length != 0)
                sInput = args[0];

            Document doc = new Document(sInput);

            Page page = doc.getPage(0);

            page.addQRBarcode("Datalogics", 72.0, page.getCropBox().getTop() - 1.5 * 72.0, 72.0, 72.0);

            doc.save(EnumSet.of(SaveFlags.FULL), sOutput);
        }
        finally {
            lib.delete();
        }
    }
}
