/*
 *
 * A sample which demonstrates how to create a new
 * PDF file and add two pages containing a series of Path and Text elements
 *
 * Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved.
 *
 */
package com.datalogics.pdfl.samples;

import com.datalogics.PDFL.CancelProc;

/**
 * Cancel proc for demonstration purposes.
 */
public class AECancelProc extends CancelProc {

    @Override
    public boolean Call() {
        return false;
    }
}
