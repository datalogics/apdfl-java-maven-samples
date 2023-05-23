/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;

/**
 * ShowBorderCommand - allows to show/hide the border rectangle for the
 * PDFL.LinkAnnotation.
 */
public class ShowBorderCommand extends UpdatePropertyCommand {
    @Override
    protected void doInner() throws FinalState {
        final boolean borderState = ((ApplicationController) getApplication()).getActiveProps().getShowBorder();
        ((ApplicationController) getApplication()).getActiveProps().setShowBorder(!borderState);
        super.doInner();
    }

    private final static CommandType type = CommandType.EDIT;
}
