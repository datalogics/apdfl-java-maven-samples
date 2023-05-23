/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive.Editors;

import java.awt.Graphics;

import com.datalogics.pdfl.JavaViewer.Views.Interfaces.PDF;

/**
 * EditAnnotationOuptut - draws annotation's shape and bounding box with guides rectangles.
 * 
 * It is also responsible for cursor view.
 */
public class EditAnnotationOuptut extends BaseAnnotationOutput {
    public EditAnnotationOuptut(PDF.Cursor cursor, boolean onlyCursor) {
        this.cursor = cursor;
        this.onlyCursor = onlyCursor;
    }

    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);
        getAnnotationEditor().drawShape(g);
    }

    @Override
    public void drawSelection(Graphics g) {
        super.drawSelection(g);
        getAnnotationEditor().drawBoundingRect(g);
        getAnnotationEditor().drawGuides(g);
    }

    @Override
    public void changeCursor(Hit hit) {
        super.changeCursor(hit);
        if (hit.noHit() && !onlyCursor) {
            revertCursor();
        } else {
            applyCursor(onlyCursor || hit.getGuide() == null ? cursor : hit.getGuide().getCursor());
        }
    }

    private PDF.Cursor cursor;
    private boolean onlyCursor;
}
