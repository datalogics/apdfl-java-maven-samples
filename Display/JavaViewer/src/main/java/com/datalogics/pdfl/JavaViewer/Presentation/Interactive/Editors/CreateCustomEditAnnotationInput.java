/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive.Editors;

import com.datalogics.pdfl.JavaViewer.Presentation.Interactive.EditorStates;
import com.datalogics.pdfl.JavaViewer.Views.Interfaces.InputData;

/**
 * CreateCustomEditAnnotationInput - extends CreateRectangularAnnotationInput
 * and overrides doComplete() method from it.
 * 
 * This class is used for complex annotation type (LinkAnnotation,
 * FreeTextAnnotation).
 */
public class CreateCustomEditAnnotationInput extends CreateRectangularAnnotationInput {
    @Override
    protected void doComplete(InputData input) {
        getAnnotationEditor().setState(EditorStates.CUSTOM_EDIT);
    }
}
