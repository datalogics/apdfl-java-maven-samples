/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;

/**
 * ChangeLineWidthCommand - allows to set line width for annotations which
 * support this property.
 */
public class ChangeLineWidthCommand extends UpdatePropertyCommand {
    public ChangeLineWidthCommand(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    @Override
    protected void doInner() throws FinalState {
        ((ApplicationController) getApplication()).getActiveProps().setLineWidth(lineWidth);
        super.doInner();
    }

    private final static CommandType type = CommandType.EDIT;

    private float lineWidth;
}
