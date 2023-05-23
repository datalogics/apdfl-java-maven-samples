/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive;

import com.datalogics.pdfl.JavaViewer.Presentation.Interactive.Editors.BaseAnnotationEditor;

/**
 * EditorListener - an interface which notifies its implementers when annotation
 * state (Edit, Create, Idle etc.) has been changed.
 */
public interface EditorListener {
    /**
     * send when annotation state was changed
     * 
     * @param originator - sender of event
     * @param oldState - old annotation state
     * @param newState - new annotation state
     */
    public void stateChanged(BaseAnnotationEditor originator, Enum<?> oldState, Enum<?> newState);
}
