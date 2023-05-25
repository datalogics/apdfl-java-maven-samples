/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Annotations;

/**
 * This interface notifies its implementers about annotation update of different
 * types.
 * 
 * Main types of updates are: - annotation hidden/shown
 * (AnnotationHolder.AppearanceUpdate.OBJECT_HIDDEN/AnnotationHolder
 * .AppearanceUpdate.OBJECT_SHOWN); - annotation's shape changed - it's moved or
 * resized (AnnotationHolder.AppearanceUpdate.SHAPE_CHANGED); - annotation's
 * properties changed (AnnotationHolder.AppearanceUpdate.PROPERTIES_CHANGED)
 * other then move or resize.
 */
public interface AnnotationListener {
    void update(Object updateData);
}
