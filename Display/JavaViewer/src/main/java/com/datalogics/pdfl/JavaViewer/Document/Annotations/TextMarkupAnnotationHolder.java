/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Annotations;

import java.util.List;

import com.datalogics.PDFL.Annotation;

/**
 * TextMarkupAnnotationHolder - the child of the BaseAnnotationHolder.
 * 
 * This class wraps PDFL.TextMarkupAnnotation and allows work with quads.
 */
public class TextMarkupAnnotationHolder extends BaseAnnotationHolder {
    @Override
    protected void init(Annotation annotation, int index, int pageIndex) {
        super.init(annotation, index, pageIndex);
    }

    @Override
    protected void doReadProperties() {
        super.doReadProperties();
        readPropertyByName(AnnotationConsts.QUADS, List.class /*List<Quads>*/, true);
    }
}
