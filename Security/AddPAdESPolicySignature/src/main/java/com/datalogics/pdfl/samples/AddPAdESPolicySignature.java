/*
 *
 * This sample program demonstrates the use of AddDigitalSignature for PAdES
 * (PDF Advanced Electronic Signatures) with an explicit signature policy.
 * PAdES policy signatures (EPES) conform to the ETSI EN 319 142 standard,
 * use the ETSI.CAdES.detached SubFilter, and embed one or more signature
 * policy identifiers along with optional policy qualifiers.
 *
 * Copyright (c) 2026, Datalogics, Inc. All rights reserved.
 *
 */

package com.datalogics.pdfl.samples;

import com.datalogics.PDFL.*;

public class AddPAdESPolicySignature {

    public static void main(String [] args) throws Exception {
        System.out.println("AddPAdESPolicySignature sample:");

        Library lib = new Library();
        try {
            String sInput = Library.getResourceDirectory() + "Sample_Input/SixPages.pdf";
            String sLogo = Library.getResourceDirectory() + "Sample_Input/ducky_alpha.tif";
            String sOutput = "PAdESPolicySignature-out.pdf";

            String sPEMCert = Library.getResourceDirectory() + "Sample_Input/Credentials/PEM/ecSecP521r1Cert.pem";
            String sPEMKey = Library.getResourceDirectory() + "Sample_Input/Credentials/PEM/ecSecP521r1Key.pem";

            if (args.length > 0)
                sInput = args[0];

            if (args.length > 1)
                sOutput = args[1];

            if (args.length > 2)
                sLogo = args[2];

            System.out.println("Applying a PAdES policy digital signature to " + sInput + " with a logo " + sLogo + " and saving it as " + sOutput);

            Document doc = new Document(sInput);

            SignDoc sigDoc = new SignDoc();

            // Setup Sign params
            sigDoc.setFieldID(SignatureFieldID.CREATE_FIELD_WITH_QUALIFIED_NAME);
            sigDoc.setFieldName("Signature_es_:signatureblock");

            // Set credential related attributes
            // PAdES signatures use SHA-384 digest with EC credentials
            sigDoc.setDigestCategory(DigestCategory.SHA_384);
            sigDoc.setCredentialDataFormat(CredentialDataFmt.NON_PFX);
            sigDoc.setNonPfxSignerCert(sPEMCert, 0, CredentialStorageFmt.ON_DISK);
            sigDoc.setNonPfxPrivateKey(sPEMKey, 0, CredentialStorageFmt.ON_DISK);

            // Set the signature type to PAdES (PDF Advanced Electronic Signatures).
            // NOTE: Signature type must be set prior to adding policies.
            sigDoc.setDocSignType(SignatureType.PADES);

            // Define a signature policy (SigPolicyId) using an OID.
            sigDoc.addSigPolicy("2.16.724.1.3.1.1.2.1.9");

            // Add a policy qualifier (SPuri) pointing to the policy specification document.
            sigDoc.addSigPolicyQualifierURI(
                "https://sede.administracion.gob.es/politica_de_firma_anexo_1.pdf");

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
