package com.datalogics.pdfl.samples;

import java.util.EnumSet;

import com.datalogics.PDFL.Document;
import com.datalogics.PDFL.Library;
import com.datalogics.PDFL.OfficeCommentRendering;
import com.datalogics.PDFL.OfficeConvertInfo;
import com.datalogics.PDFL.OfficeDiagnostic;
import com.datalogics.PDFL.OfficeToPDFConvertParams;
import com.datalogics.PDFL.SaveFlags;

/*
 * This sample demonstrates the OfficeToPDF plugin, which converts a Microsoft
 * Word (.docx) document into a PDF.
 *
 * By default the sample converts DOCXLink.docx from the Sample_Input
 * directory and writes the result to ConvertWordToPDF-out.pdf in the current
 * working directory. The optional first argument is the input .docx path and
 * the optional second argument is the output PDF path.
 *
 * Document.fromOfficeFile takes the input path, an OfficeToPDFConvertParams
 * and an OfficeConvertInfo. It returns the converted Document, and fills the
 * OfficeConvertInfo with the page count and the per-asset diagnostics.
 *
 * Copyright (c) 2026, Datalogics, Inc. All rights reserved.
 *
 */

public class ConvertWordToPDF {

    /**
     * @param args optional input .docx path and output PDF path
     */
    public static void main(String[] args) throws Throwable {
        System.out.println("ConvertWordToPDF sample:");

        Library lib = new Library();

        try {
            String input = args.length > 0 ? args[0]
                    : Library.getResourceDirectory() + "Sample_Input/DOCXLink.docx";
            String output = args.length > 1 ? args[1] : "ConvertWordToPDF-out.pdf";

            System.out.println("Converting " + input + " and saving as " + output);

            // Configure the conversion. One OfficeToPDFConvertParams serves any
            // number of conversions.
            OfficeToPDFConvertParams params = new OfficeToPDFConvertParams();

            // OfficeCommentRendering.OMIT (default), MARGIN, or ANNOTATIONS.
            params.setComments(OfficeCommentRendering.MARGIN);

            // A fixed instant is stamped as /CreationDate and /ModDate and used
            // for DATE and TIME fields, so every conversion carries the same
            // dates. Leave it unset to stamp the system clock instead.
            params.setConversionTime(2026, 1, 1, 0, 0, 0);

            OfficeConvertInfo info = new OfficeConvertInfo();

            Document doc;
            try {
                doc = Document.fromOfficeFile(input, params, info);
            }
            catch (RuntimeException e) {
                // The message names the reason: the source is missing, not a
                // valid .docx, or password-protected, or the OfficeToPDF plugin
                // is not available.
                System.out.println("Conversion failed: " + e.getMessage());
                return;
            }

            System.out.println("Successfully converted the document: "
                    + info.getPageCount() + " page(s).");

            // A successful conversion can still report diagnostics: a
            // substituted font, an undecodable image, a hyperlink dropped as
            // unsafe. They describe the rendered assets; they do not make the
            // conversion fail.
            for (OfficeDiagnostic diagnostic : info.getDiagnostics()) {
                System.out.println("  [" + diagnostic.getKind() + "] " + diagnostic.getAsset()
                        + ": " + diagnostic.getMessage());
            }

            doc.save(EnumSet.of(SaveFlags.FULL), output);
            System.out.println("Saved to " + output);
            doc.delete();
        }
        finally {
            lib.delete();
        }
    }
}
