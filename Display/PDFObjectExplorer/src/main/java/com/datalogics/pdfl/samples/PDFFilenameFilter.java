/*
 * Copyright (c) 2009-2023, Datalogics, Inc. All rights reserved.
 *
 *
 * ============================ PDFFilenameFilter ===================================
 * This filter is used with the file dialog to limit displayed files to those
 * with a .pdf extension (and directories).
 */
package com.datalogics.pdfl.samples;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 */
public class PDFFilenameFilter implements FilenameFilter {

    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(".pdf");
    }
}
