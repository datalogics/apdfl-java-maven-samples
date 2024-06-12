
package com.datalogics.pdfl.samples;

/*
 * The ExportFormsData sample demonstrates how to Export forms data from XFA and AcroForms documents:
 *
 *  - Export data from a XFA (Dynamic or Static) document, the types supported include XDP, XML, or XFD
 *  - Export data from an AcroForms document, the types supported include XFDF, FDF, or XML
 */
import com.datalogics.PDFL.*;
import java.util.EnumSet;
public class ExportFormsData
{

    public static void main(String[] args) throws Throwable {
        System.out.println("ExportFormsData sample:");

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
            String sOutput = "../ExportFormsDataXFA.xdp";

            if (args.length > 0)
            {
                sOutput = args[0];
            }

            Document doc = new Document(sInput);

            //Export the data while specifying the type, in this case XDP
            boolean result = doc.exportXFAFormsData(sOutput, XFAFormExportType.XDP);

            if (result)
            {
                System.out.println("Forms data was exported!");
            }
            else
            {
                System.out.println("Exporting of Forms data failed!");
            }

            doc.delete();

            //AcroForms document
            sInput = Library.getResourceDirectory() + "Sample_Input/AcroForm.pdf";
            sOutput = "../ExportFormsDataAcroForms.xfdf";

            if (args.length > 1)
            {
                sOutput = args[1];
            }

            doc = new Document(sInput);

            //Export the data while specifying the type, in this case XFDF
            result = doc.exportAcroFormsData(sOutput, AcroFormExportType.XFDF);

            if (result)
            {
                System.out.println("Forms data was exported!");
            }
            else
            {
                System.out.println("Exporting of Forms data failed!");
            }

            doc.delete();
        }
        finally {
            lib.delete();
        }
   }
}

