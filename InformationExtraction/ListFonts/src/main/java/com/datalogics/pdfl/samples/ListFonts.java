package com.datalogics.pdfl.samples;

import com.datalogics.PDFL.*;
import java.util.*;
/*
 * 
 * A sample which demonstrates how to list
 * the set of available fonts.
 * 
 * This sample lists all of the fonts available for use on the machine where the ListFont program is run.
 * The font name appears with the type, such as Type0, Type1, or TrueType, and the encoding method,
 * such as WinAnsiEncoding.
 * 
 * Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved.
 *
 */
public class ListFonts {
	public static void main(String[] args) throws Throwable {
		Library lib = new Library();

		try {
			List<Font> fonts = Font.getFontList();

			for (Font font : fonts) {
				System.out.println(font.getName());
				System.out.println(font.getType());
				System.out.println(font.getEncoding());
			}
		} finally {
			lib.delete();
		}
	}

};
