/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive.Editors;

import java.awt.Graphics;

/**
 * LinkTargetOutput - draws Link annotation shape when it has been selected.
 */
public class LinkTargetOutput extends BaseAnnotationOutput {
    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);
        getAnnotationEditor().drawShape(g);
    }

    @Override
    public void drawSelection(Graphics g) {
        // no border for link annotation targeting mode
    }
}
