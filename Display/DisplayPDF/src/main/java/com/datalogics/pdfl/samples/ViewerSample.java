package com.datalogics.pdfl.samples;

/*
 * 
 * A sample which demonstrates the use of the API to view a PDF
 * file and search for text in the file and highlight them.
 *
 * Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved.
 *
 */
import javax.swing.*;

public class ViewerSample {

	static ViewerFrame frame;
	
	public static void main(String[] args) {
		// Do OS specific setup.
		platformSetup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame = new ViewerFrame();
                frame.setVisible(true);
            }
        });
	}

	private static void platformSetup() {
        if (System.getProperty("os.name").toLowerCase().startsWith("mac os x"))
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
