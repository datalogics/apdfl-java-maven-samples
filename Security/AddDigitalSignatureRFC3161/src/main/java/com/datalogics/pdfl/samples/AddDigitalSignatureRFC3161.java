/*
 *
 * This sample program demonstrates the use of AddDigitalSignature for RFC3161/TimeStamp signature type.
 *
 * Copyright (c) 2025, Datalogics, Inc. All rights reserved.
 *
 */

package com.datalogics.pdfl.samples;

import java.util.EnumSet;

import com.datalogics.PDFL.*;

public class AddDigitalSignatureRFC3161 {

    public static void main(String [] args) throws Exception {
        System.out.println("AddDigitalSignatureRFC3161 sample:");

        Library lib = new Library(); 
        try {
            Document doc = new Document();
            
            String sInput = Library.getResourceDirectory() + "Sample_Input/CreateAcroForm2h.jpg";

            String sOutput = "DigSigRFC3161-out.pdf";

            if (args.length > 0)
                sInput = args[0];

            if (args.length > 1)
                sOutput = args[1];

            System.out.println("Applying an RFC3161/TimeStamp digital signature to " + sInput + " and saving it as " + sOutput);

            SignDoc sigDoc = new SignDoc();

            // Setup Sign params
            sigDoc.setFieldID(SignatureFieldID.SearchForFirstUnsignedField);

            // Set credential related attributes
            sigDoc.setDigestCategory(com.datalogics.PDFL.DigestCategory.Sha256);

            // Set the signature type to be used, RFC3161/TimeStamp.
            // The available types are defined in the SignatureType enum. Default CMS.
            sigDoc.setDocSignType(SignatureType.RFC3161);

            // Setup Save params
            sigDoc.setOutputPath(sOutput);

            // Finally, sign and save the document
            sigDoc.AddDigitalSignature(doc);
        }
        finally {
            lib.delete();
        }
    }
}

