/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;

/**
 * AutosizeVerticallyCommand - extends the UpdatePropertyCommand class.
 * 
 * This class allows to set auto size for FreeTextAnnotation.
 */
public class AutosizeVerticallyCommand extends UpdatePropertyCommand {
    @Override
    protected void doInner() throws FinalState {
        ((ApplicationController) getApplication()).getActiveProps().setAutoSize(true);
        super.doInner();
    }

    private final static CommandType type = CommandType.EDIT;
}
