package com.datalogics.pdfl.samples;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

import com.datalogics.PDFL.Content;
import com.datalogics.PDFL.Document;
import com.datalogics.PDFL.Form;
import com.datalogics.PDFL.Image;
import com.datalogics.PDFL.Library;
import com.datalogics.PDFL.Matrix;
import com.datalogics.PDFL.OCREngine;
import com.datalogics.PDFL.OCRParams;
import com.datalogics.PDFL.Page;
import com.datalogics.PDFL.LanguageSetting;
import com.datalogics.PDFL.Language;
import com.datalogics.PDFL.Rect;
import com.datalogics.PDFL.SaveFlags;

/*
 * Runs OCR on the document recognizing text found on its rasterized pages.
 * 
 * Copyright (c) 2007-2025, Datalogics, Inc. All rights reserved.
 *
 */

public class OCRDocument {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("OCRDocument sample:");

        String sInput = Library.getResourceDirectory() + "Sample_Input/scanned_images.pdf";
        String sOutput = "OCRDocument-out.pdf";

        if (args.length > 0)
            sInput = args[0];
        if (args.length > 1)
            sOutput = args[1];

        System.out.println("Input file: " + sInput + ", will write to " + sOutput);

        Library lib = new Library();

        try {
            OCRParams ocrParams = new OCRParams();
            //The OCRParams.setLanguages method controls which languages the OCR engine attempts
            //to detect. By default the OCR engine searches for English.
            List<LanguageSetting> langList = new ArrayList<LanguageSetting>();
            LanguageSetting languageOne = new LanguageSetting(Language.ENGLISH, false);
            langList.add(languageOne);
            //You could add additional languages for the OCR engine to detect by adding 
            //more entries to the LanguageSetting list.
            //LanguageSetting languageTwo = new LanguageSetting(Language.JAPANESE, false);
            //langList.add(languageTwo);
            ocrParams.setLanguages(langList);

            // If your image resolution is not 300 dpi, specify it here. Specifying a
            // correct resolution gives better results for OCR, especially with
            // automatic image preprocessing.
            // ocrParams.setResolution(600);

            OCREngine ocrEngine = new OCREngine(ocrParams);
            // Open a document with a single page.
            Document doc = new Document();
            // Create a PDF page around this image. The design width and height
            // for the image are carried in the Image's Matrix's A and D fields, respectively.
            Image newImage = new Image(sInput, doc);
            Matrix imageMatrix = newImage.getMatrix();
            Rect pageRect = new Rect(0, 0, imageMatrix.getA(), imageMatrix.getD());
            Page docpage = doc.createPage(Document.BEFORE_FIRST_PAGE, pageRect);
            Page page = doc.getPage(0);
            try {
                page.recognizePageContents(page, ocrEngine);
            }
            finally {
                page.delete();
                ocrEngine.delete();
            }
            doc.save(EnumSet.of(SaveFlags.FULL), sOutput);
            doc.delete();
        }
         finally {
            lib.delete();
        }
    }

}
