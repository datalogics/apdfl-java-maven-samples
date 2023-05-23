/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.PDFL.HorizontalAlignment;
import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;

/**
 * ChangeFontAlignmentCommand - allows to change alignment for annotations which
 * support this property.
 */
public class ChangeFontAlignmentCommand extends UpdatePropertyCommand {
    public ChangeFontAlignmentCommand(HorizontalAlignment align) {
        this.align = align;
    }

    @Override
    protected void doInner() throws FinalState {
        ((ApplicationController) getApplication()).getActiveProps().setQuadding(align);
        super.doInner();
    }

    private final static CommandType type = CommandType.EDIT;

    private HorizontalAlignment align;
}
