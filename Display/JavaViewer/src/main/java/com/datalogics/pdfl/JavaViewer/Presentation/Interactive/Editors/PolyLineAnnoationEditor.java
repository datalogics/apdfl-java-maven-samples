/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive.Editors;

import java.awt.Graphics2D;

/**
 * PolyLineAnnotationEditor - draws polyline annotation on the page.
 */
public class PolyLineAnnoationEditor extends PolyAnnotationEditor {
    @Override
    protected void doDrawShape(Graphics2D gr) {
        gr.setColor(getProperties().getForeColor());
        gr.drawPolyline(getXPoints(), getYPoints(), getPointCount());
    }
}
