/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Annotations;

import com.datalogics.PDFL.Annotation;
import com.datalogics.PDFL.LineEndingStyle;

/**
 * PolylineAnnotationHolder - the child of the PolyAnnotationHolder.
 * 
 * This class wraps PDFL.PolyLineAnnotation and sets additional properties for
 * it.
 */
public class PolylineAnnotationHolder extends PolyAnnotationHolder {
    @Override
    protected void init(Annotation annotation, int index, int pageIndex) {
        super.init(annotation, index, pageIndex);
    }

    @Override
    protected void doReadProperties() {
        super.doReadProperties();

        readPropertyByName(AnnotationConsts.START_ENDING_STYLE, LineEndingStyle.class, false);
        readPropertyByName(AnnotationConsts.END_ENDING_STYLE, LineEndingStyle.class, false);
    }
}
