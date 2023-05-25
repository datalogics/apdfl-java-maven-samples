/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;

/**
 * ChangeFontSizeCommand - allows to set font size for annotations which support
 * this property.
 */
public class ChangeFontSizeCommand extends UpdatePropertyCommand {
    public ChangeFontSizeCommand(int fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    protected void doInner() throws FinalState {
        ((ApplicationController) getApplication()).getActiveProps().setFontSize(fontSize);
        super.doInner();
    }

    private final static CommandType type = CommandType.EDIT;

    private int fontSize;
}
