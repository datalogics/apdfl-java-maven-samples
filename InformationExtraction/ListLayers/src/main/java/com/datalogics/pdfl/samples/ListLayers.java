package com.datalogics.pdfl.samples;
/*
 * 
 * A sample which demonstrates how to list the
 * separate color layers of a PDF file.
 *
 * This sample searches for and lists the names of the color layers found in a PDF document. 
 *
 * Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved.
 *
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.datalogics.PDFL.Document;
import com.datalogics.PDFL.Library;
import com.datalogics.PDFL.OptionalContentContext;
import com.datalogics.PDFL.OptionalContentGroup;

public class ListLayers {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
        System.out.println("ListLayers sample:");

    	Library lib = new Library();

		try {
	        String filename = Library.getResourceDirectory() + "Sample_Input/Layers.pdf";
	        if (args.length > 0) {
	            filename = args[0];
                }
	        System.out.println("Layers in file: " + filename ); 
	        Document doc = new Document(filename);

	        List<OptionalContentGroup> ocgs = doc.getOptionalContentGroups();
	        for (OptionalContentGroup ocg : ocgs)
	        {
	        	System.out.println(ocg.getName());
	        	System.out.print("  Intent: ");
	        	System.out.println(ocg.getIntent());
	        }
	        
	        OptionalContentContext ctx = doc.getOptionalContentContext();
	        System.out.print("Optional content states: ");
	        System.out.println(ctx.getOCGStates(ocgs));
		}
		finally {
			lib.delete();
		}
	}
}
