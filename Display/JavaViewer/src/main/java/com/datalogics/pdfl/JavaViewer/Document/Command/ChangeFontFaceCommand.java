/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;

/**
 * ChangeFontFaceCommand - allows to update font face for annotations which
 * support this property.
 */
public class ChangeFontFaceCommand extends UpdatePropertyCommand {
    public ChangeFontFaceCommand(String fontFace) {
        this.fontFace = fontFace;
    }

    @Override
    protected void doInner() throws FinalState {
        ((ApplicationController) getApplication()).getActiveProps().setFontFace(fontFace);
        super.doInner();
    }

    private final static CommandType type = CommandType.EDIT;

    private String fontFace;
}
