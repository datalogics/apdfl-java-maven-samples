package com.datalogics.pdfl.samples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.EnumSet;

import com.datalogics.PDFL.SaveFlags;
import com.datalogics.PDFL.Document;
import com.datalogics.PDFL.Library;
import com.datalogics.PDFL.PDFAConvertParams;
import com.datalogics.PDFL.PDFAConvertType;
import com.datalogics.PDFL.PDFAConvertResult;

/*
  * 
 * This sample demonstrates converting a standard PDF document into a
 * PDF Archive, or PDF/A, compliant version of a PDF file.
 *
 * Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved.
 *
 */
public class PDFAConverter
{
    public static void main(String[] args) throws Exception {
        System.out.println("PDFAConverter Sample:");

        Library lib = new Library();
        try {
            System.out.println("Initialzed the library.");
            String sInput = Library.getResourceDirectory() + "Sample_Input/ducky.pdf";
            String sOutput = "PDFAConverter-out.pdf";
            if (args.length > 0)
                sInput = args[0];
            if (args.length > 1)
                sOutput = args[1];
            System.out.println("Converting " + sInput );

            Document doc = new Document(sInput);

            // Make a conversion parameters object
            PDFAConvertParams pdfaParams = new PDFAConvertParams();
            pdfaParams.setAbortIfXFAIsPresent(true);
            pdfaParams.setIgnoreFontErrors(false);
            pdfaParams.setNoValidationErrors(false);
            pdfaParams.setValidateImplementationLimitsOfDocument(true);

            // Create a PDF/A compliant version of the document
            PDFAConvertResult pdfaResult = doc.cloneAsPDFADocument(PDFAConvertType.RGB_3B, pdfaParams);

            // The conversion may have failed: we must check if the result has a valid Document
            if (pdfaResult.getPDFADocument() == null)
            {
                System.out.println("ERROR: Could not convert " + sInput + " to PDF/A.");
            }
            else
            {
                System.out.println("Successfully converted " + sInput + " to PDF/A.");

                Document pdfaDoc = pdfaResult.getPDFADocument();

                //Save the result.
                pdfaDoc.save(pdfaResult.getPDFASaveFlags(), sOutput);
            }

            doc.delete();
        }
        finally
        {
            lib.delete();
        }
    }
}
