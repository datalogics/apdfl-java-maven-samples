/*
 *
 * This sample program demonstrates the use of AddDigitalSignature for CMS signature type.
 *
 * Copyright (c) 2025, Datalogics, Inc. All rights reserved.
 *
 */

package com.datalogics.pdfl.samples;

import com.datalogics.PDFL.*;

public class AddDigitalSignatureCMS {

    public static void main(String [] args) throws Exception {
        System.out.println("AddDigitalSignatureCMS sample:");

        Library lib = new Library(); 
        try {
            String sInput = Library.getResourceDirectory() + "Sample_Input/SixPages.pdf";
            String sLogo = Library.getResourceDirectory() + "Sample_Input/ducky_alpha.tif";
            String sOutput = "DigSigCMS-out.pdf";

            String sDERCert = Library.getResourceDirectory() + "Sample_Input/Credentials/DER/RSA_certificate.der";
            String sDERKey = Library.getResourceDirectory() + "Sample_Input/Credentials/DER/RSA_privKey.der";

            if (args.length > 0)
                sInput = args[0];

            if (args.length > 1)
                sOutput = args[1];

            if (args.length > 2)
                sOutput = args[2];

            System.out.println("Applying a CMS digital signature to " + sInput + "with a logo " + sLogo + " and saving it as " + sOutput);

            Document doc = new Document(sInput);

            SignDoc sigDoc = new SignDoc();

            // Setup Sign params
            sigDoc.setFieldID(SignatureFieldID.CREATE_FIELD_WITH_QUALIFIED_NAME);
            sigDoc.setFieldName("Signature_es_:signatureblock");

            // Set credential related attributes
            sigDoc.setDigestCategory(DigestCategory.SHA_256);
            sigDoc.setCredentialDataFormat(CredentialDataFmt.NON_PFX);
            sigDoc.setNonPfxSignerCert(sDERCert, 0, CredentialStorageFmt.ON_DISK);
            sigDoc.setNonPfxPrivateKey(sDERKey, 0, CredentialStorageFmt.ON_DISK);

            // Set the signature type to be used.
            // The available types are defined in the SignatureType enum. Default CMS.
            sigDoc.setDocSignType(SignatureType.CMS);

            // Setup the signer information
            // (Logo image is optional)
            sigDoc.setSignerInfo(sLogo, 0.5F, "John Doe", "Chicago, IL", "Approval", "Datalogics, Inc.", DisplayTraits.KDISPLAY_ALL);

            // Set the size and location of the signature box (optional)
            // If not set, invisible signature will be placed on first page
            sigDoc.setSignatureBoxPageNumber(0);
            sigDoc.setSignatureBoxRectangle(new Rect(100, 300, 400, 400));

            // Setup Save params
            sigDoc.setOutputPath(sOutput);

            // Finally, sign and save the document
            sigDoc.addDigitalSignature(doc);
        }
        finally {
            lib.delete();
        }
    }
}

