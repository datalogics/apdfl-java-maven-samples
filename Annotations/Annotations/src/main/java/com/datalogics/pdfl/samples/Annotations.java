package com.datalogics.pdfl.samples;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.datalogics.PDFL.Annotation;
import com.datalogics.PDFL.Document;
import com.datalogics.PDFL.Library;
import com.datalogics.PDFL.Page;
/*
 * 
 * This sample demonstrates how to find and describe annotations in an existing PDF document.
 * 
 * Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved.
 *
 */
public class Annotations {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        System.out.println("Annotation sample:");

        Library lib = new Library();
        try {

		String filename = Library.getResourceDirectory() + "Sample_Input/sample_annotations.pdf";
        	if (args.length > 0) {
        		filename = args[0];
                }
                System.out.println("Annotations found in file " + filename + ":");

        	Document doc = new Document(filename);

        	Page pg = doc.getPage(0);
        	
                int nAnnots = pg.getNumAnnotations();
                for (int i = 0; i < nAnnots; i++) {
                        Annotation ann = pg.getAnnotation(i);
                        System.out.format("Annotation: %s is a %s\n", ann.getTitle(), ann.getClass().getName());
                }

        	
        } 
        finally
        {
        	lib.delete();
        }

	}

}
