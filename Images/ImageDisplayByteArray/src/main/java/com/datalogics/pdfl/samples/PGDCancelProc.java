package com.datalogics.pdfl.samples;

/*
Copyright (c) 2007-2023, Datalogics, Inc. All rights reserved. 
 */
import java.awt.*;
import java.awt.Point;
import java.awt.image.*;
import java.nio.ByteOrder;
import java.util.EnumSet;

import javax.swing.*;

import com.datalogics.PDFL.*;

public class PGDCancelProc extends CancelProc {
    private boolean called;
    private boolean canceled;
    private boolean should_cancel;
    PGDCancelProc(boolean can) {
	called = false;
	canceled = false;
	should_cancel = can;
    }
    public boolean Call() {
	called = true;
	canceled = should_cancel;
	System.out.print("Called cancel procedure\n");
	return should_cancel;
    }
};
