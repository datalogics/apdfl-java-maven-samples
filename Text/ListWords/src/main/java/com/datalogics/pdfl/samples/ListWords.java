package com.datalogics.pdfl.samples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import com.datalogics.PDFL.*;

/*
 * 
 * This sample lists the text for the words in a PDF document.
 * 
 * Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved.
 *
 */

public class ListWords {
    public static void main (String[] args) throws Throwable
    {
        System.out.println("ListWords sample:");

    	Library lib = new Library();

		try {
	        String filename = Library.getResourceDirectory() + "Sample_Input/sample.pdf";
	        if (args.length > 0) {
	            filename = args[0];
                }
	        
                System.out.println("Words in file " + filename + ":");
	       	Document doc = new Document(filename);
			
			int nPages = doc.getNumPages();
			
			System.out.println("Pages=" + Integer.toString(nPages));
			
			WordFinderConfig wordConfig = new WordFinderConfig();
			wordConfig.setDisableTaggedPDF(true);
			wordConfig.setIgnoreCharGaps(true);
			
			WordFinder wordFinder = new WordFinder(doc, WordFinderVersion.LATEST, wordConfig);

            List<Word> pageWords = null;
            
            for (int i = 0; i < nPages; i++)
            {
                pageWords = wordFinder.getWordList(i);
                for ( Word w : pageWords )
                {
                	System.out.println(w.getText());
                	System.out.println(w.getQuads().toString());
                    System.out.println(w.getStyleTransitions());
                    System.out.println(w.getAttributes().toString());
                }
            }
			doc.close();
		}
		finally {
			lib.delete();
		}
	}
};
