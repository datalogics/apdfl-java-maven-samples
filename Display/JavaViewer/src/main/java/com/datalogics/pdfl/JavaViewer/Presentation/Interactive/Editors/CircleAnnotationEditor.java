/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive.Editors;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.datalogics.pdfl.JavaViewer.Document.Annotations.AnnotationProperties;

/**
 * CircleAnnotationEditor - allows to draw circle annotation.
 */
public class CircleAnnotationEditor extends BaseAnnotationEditor {
    @Override
    protected void doDrawShape(Graphics2D gr) {
        final AnnotationProperties annotationProperties = getProperties();
        final int penWidth = (int) Math.round(annotationProperties.getLineWidth() * getPageModel().getScale());

        Rectangle boundingRect = getPageModel().transform(annotationProperties.getBoundingRect());
        boundingRect.grow(-penWidth / 2, -penWidth / 2); // consider pen width for circle bounds

        if (annotationProperties.hasFill()) {
            gr.setColor(annotationProperties.getInteriorColor());
            gr.fillOval(boundingRect.x, boundingRect.y, boundingRect.width, boundingRect.height);
        }
        gr.setColor(annotationProperties.getForeColor());
        gr.drawOval(boundingRect.x, boundingRect.y, boundingRect.width, boundingRect.height);
    }
}
