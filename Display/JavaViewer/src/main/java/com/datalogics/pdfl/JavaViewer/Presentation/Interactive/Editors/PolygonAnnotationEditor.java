/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive.Editors;

import java.awt.Graphics2D;

/**
 * PolygonAnnotationEditor - draws polygon annotation on the page.
 */
public class PolygonAnnotationEditor extends PolyAnnotationEditor {
    @Override
    protected void doDrawShape(Graphics2D gr) {
        if (getProperties().hasFill()) {
            gr.setColor(getProperties().getInteriorColor());
            gr.fillPolygon(getXPoints(), getYPoints(), getPointCount());
        }
        gr.setColor(getProperties().getForeColor());
        gr.drawPolygon(getXPoints(), getYPoints(), getPointCount());
    }
}
