/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive.Editors;

import java.awt.Dimension;
import java.awt.Rectangle;

import com.datalogics.PDFL.Rect;
import com.datalogics.pdfl.JavaViewer.Views.Interfaces.InputData;

/**
 * CreateRectangularAnnotationInput - allows to create PDFL.CircleAnnotation and PDFL.SquareAnnotation.
 * 
 * It sets starting bounding box and returns handling to the base class.
 */
public class CreateRectangularAnnotationInput extends CreateAnnotationInput {
    @Override
    protected void createNewGuide(InputData input) {
        final Rect boundingRect = getAnnotationEditor().getPageModel().transform(new Rectangle(input.getLocation(), new Dimension(1, 1)));
        getAnnotationEditor().getHolder().getProperties().setBoundingRect(boundingRect);
        setCapturedGuide(GuideRectangle.Layout.SE);
    }
}
