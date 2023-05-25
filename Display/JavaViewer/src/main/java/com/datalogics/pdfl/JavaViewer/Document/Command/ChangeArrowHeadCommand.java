/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.PDFL.LineEndingStyle;
import com.datalogics.pdfl.JavaViewer.Document.Annotations.AnnotationProperties;
import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;

/**
 * ChangeArrowHeadCommand - allows to modify arrow style for annotations which
 * support this property.
 */
public class ChangeArrowHeadCommand extends UpdatePropertyCommand {
    public ChangeArrowHeadCommand(LineEndingStyle style, boolean startEnd) {
        this.style = style;
        this.startEnd = startEnd;
    }

    @Override
    protected void doInner() throws FinalState {
        final AnnotationProperties props = ((ApplicationController) getApplication()).getActiveProps();
        if (startEnd)
            props.setStartEndingStyle(style);
        else
            props.setEndEndingStyle(style);
        super.doInner();
    }

    private final static CommandType type = CommandType.EDIT;

    private LineEndingStyle style;
    private boolean startEnd;
}
