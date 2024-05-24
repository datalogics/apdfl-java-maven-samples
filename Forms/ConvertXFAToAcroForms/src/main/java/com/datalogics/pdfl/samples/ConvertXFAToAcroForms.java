package com.datalogics.pdfl.samples;

/*
 * Converts XFA (Dynamic or Static) fields to AcroForms fields and removes XFA fields.
 *
 * Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved.
 */
import com.datalogics.PDFL.*;
import java.util.EnumSet;
public class ConvertXFAToAcroForms
{

    public static void main(String[] args) throws Throwable {
        System.out.println("ConvertXFAToAcroForms sample:");

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
            String sOutput = "../ConvertXFAToAcroForms-out.pdf";

            if (args.length > 0)
            {
                sInput = args[0];
            }

            if (args.length > 1)
            {
                sOutput = args[1];
            }

            Document doc = new Document(sInput);
            long pagesOutput = doc.convertXFAFieldsToAcroFormFields();

            System.out.println("XFA document was converted into AcroForms document with " + pagesOutput + " pages.");

            doc.save(EnumSet.of(SaveFlags.FULL, SaveFlags.LINEARIZED), sOutput);

            doc.delete();
        }
        finally {
            lib.delete();
        }
   }
}

