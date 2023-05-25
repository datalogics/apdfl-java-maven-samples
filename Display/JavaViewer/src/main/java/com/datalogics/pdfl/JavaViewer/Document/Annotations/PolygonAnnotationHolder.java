/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Annotations;

/**
 * PolygonAnnotationHolder - the child of the PolyAnnotationHolder.
 * 
 * This class wraps a PDFL.PolygonAnnotation object.
 */
import com.datalogics.PDFL.Annotation;

public class PolygonAnnotationHolder extends PolyAnnotationHolder {
    @Override
    protected void init(Annotation annotation, int index, int pageIndex) {
        super.init(annotation, index, pageIndex);
    }
}
