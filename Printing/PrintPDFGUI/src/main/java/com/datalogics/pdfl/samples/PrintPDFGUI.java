package com.datalogics.pdfl.samples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.datalogics.PDFL.*;

/*
 * This sample demonstrates printing a PDF file. It is similar to PrintPDF, but this
 * program provides a user interface.
 *
 * Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved.
 *
 */

@SuppressWarnings("serial")
public class PrintPDFGUI extends JFrame 
{
    JTextField PDFfilenametext;
    public PrintPDFGUI() 
    {
        super("Print PDF");

        java.awt.Container contentPane = getContentPane();

        JLabel mylabel = new JLabel("Print a PDF file");
        contentPane.setLayout(new FlowLayout());
        contentPane.add(mylabel);

        JButton PrintPDFbutton = new JButton("Click to print PDF file");
        
        PDFfilenametext = new JTextField(64);
        contentPane.add(PDFfilenametext);
        contentPane.add(PrintPDFbutton);
                
        PrintPDFbutton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                PrintPDFAction();
                PrintPDFClose();

            }
        });
    }
    
    public void PrintPDFClose() {        
        this.dispose();
    }
 
    public void PrintPDFAction() {

         Library lib = new Library();
         String filename = PDFfilenametext.getText();
         try
         {
             Document doc = new Document(filename);

             // Get some parameters 
             PrintUserParams userParams = new PrintUserParams();
             
             // Not available on some platforms yet.
             // userParams.setShrinkToFit(true);

             // Print to a file
             doc.printToFile(userParams, "PrintPDF_out.ps");

             // Print to a printer
             // For a list of the current print drivers available under WinNT, look at:
             // HKEY_CURRENT_USER\Software\Microsoft\WindowsNT\CurrentVersion\Devices
             // but some special virtual printers modify their ports, so pose a print dialog

             this.setVisible(false);
             if ( userParams.posePrintDialog(doc))

             // Or use the default printer
             // userParams.useDefaultPrinter(doc);

             {
                 userParams.setPaperHeight(PrintUserParams.USE_MEDIA_BOX);
                 userParams.setPaperWidth(PrintUserParams.USE_MEDIA_BOX);
            	 doc.print(userParams);
             }
         }
         catch (Exception err)
         {
        	 JOptionPane.showMessageDialog((Component)null,
        			 "An exception has occured: " + err.getMessage(),
        			 "An exception has occured",
        			 JOptionPane.ERROR_MESSAGE);
         } finally {
        	 // this properly terminates the library, and is required
        	 lib.delete();
         }
    }

    public static void main(String args[]) 
    {
        final JFrame f = new PrintPDFGUI();

        f.setBounds(100, 100, 750, 300);
        f.setVisible(true);
        f.setBackground(java.awt.Color.white);
        f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
