
package com.datalogics.pdfl.samples;

/*
 *
 * The ImportFormsData sample demonstrates how to Import forms data into XFA and AcroForms documents:
 *
 *  - Import data into a XFA (Dynamic or Static) document, the types supported include XDP, XML, or XFD
 *  - Import data into an AcroForms document, the types supported include XFDF, FDF, or XML
 *
 * Copyright (c) 2024, Datalogics, Inc. All rights reserved.

 */
import com.datalogics.PDFL.*;
import java.util.EnumSet;
public class ImportFormsData
{

    public static void main(String[] args) throws Throwable {
        System.out.println("ImportFormsData sample:");

        Library lib = new Library(EnumSet.of(LibraryFlags.INIT_FORMS_EXTENSION));

        try
        {
            if (!lib.isFormsExtensionAvailable())
            {
                System.out.println("Forms Plugins were not properly loaded!");
                return;
            }

            //Must be set to true to prevent default legacy behavior of PDFL
            lib.setAllowOpeningXFA(true);

            System.out.println("Initialized the library.");

            //XFA document
            String sInput = Library.getResourceDirectory() + "Sample_Input/DynamicXFA.pdf";
            String sInputData = Library.getResourceDirectory() + "Sample_Input/DynamicXFA_data.xdp";
            String sOutput = "../ImportFormsDataXFA-out.pdf";

            if (args.length > 0)
            {
                sOutput = args[0];
            }

            Document doc = new Document(sInput);

            //Import the data, acceptable types include XDP, XML, and XFD
            boolean result = doc.importXFAFormsData(sInputData);

            if (result)
            {
                System.out.println("Forms data was imported!");

                doc.save(EnumSet.of(SaveFlags.FULL, SaveFlags.LINEARIZED), sOutput);
            }
            else
            {
                System.out.println("Importing of Forms data failed!");
            }

            doc.delete();

            //AcroForms document
            sInput = Library.getResourceDirectory() + "Sample_Input/AcroForm.pdf";
            sInputData = Library.getResourceDirectory() + "Sample_Input/AcroForm_data.xfdf";
            sOutput = "../ImportFormsDataAcroForms.pdf";

            if (args.length > 1)
            {
                sOutput = args[1];
            }

            doc = new Document(sInput);

            //Import the data while specifying the type, in this case XFDF
            result = doc.importAcroFormsData(sInputData, AcroFormImportType.XFDF);

            if (result)
            {
                System.out.println("Forms data was imported!");

                doc.save(EnumSet.of(SaveFlags.FULL, SaveFlags.LINEARIZED), sOutput);
            }
            else
            {
                System.out.println("Importing of Forms data failed!");
            }

            doc.delete();
        }
        finally {
            lib.delete();
        }
   }
}

