/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive.Editors;

import java.awt.Graphics;

import com.datalogics.pdfl.JavaViewer.Views.Interfaces.PDF;

/**
 * HoverAnnotationOutput - changes cursor view and draws bounding box of the
 * hovered annotation.
 */
public class HoverAnnotationOutput extends BaseAnnotationOutput {
    @Override
    public void drawSelection(Graphics g) {
        super.drawSelection(g);
        getAnnotationEditor().drawBoundingRect(g);
    }

    @Override
    public void changeCursor(Hit hit) {
        super.changeCursor(hit);
        if (hit.noHit()) {
            revertCursor();
        } else {
            applyCursor(PDF.Cursor.HAND);
        }
    }
}
