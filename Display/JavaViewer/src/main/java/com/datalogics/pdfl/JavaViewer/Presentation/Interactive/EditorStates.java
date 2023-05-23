/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive;

/**
 * EditorStates - enum which describe annotation state depending on user
 * activity.
 */
public enum EditorStates {
    NONE, IDLE, HOVER, SELECT, EDIT, CUSTOM_EDIT, CREATE;

    public boolean isSelected() {
        return this.equals(SELECT) || this.equals(CUSTOM_EDIT) || this.equals(EDIT) || this.equals(CREATE);

    }
}
