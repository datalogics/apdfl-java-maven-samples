/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;

/**
 * ChangeDashPatternCommand - allows to modify dash pattern for annotations
 * which support this property.
 */
public class ChangeDashPatternCommand extends UpdatePropertyCommand {
    public ChangeDashPatternCommand(float[] pattern) {
        this.pattern = pattern;
    }

    @Override
    protected void doInner() throws FinalState {
        ((ApplicationController) getApplication()).getActiveProps().setDashPattern(pattern);
        super.doInner();
    }

    private final static CommandType type = CommandType.EDIT;

    private float[] pattern;
}
