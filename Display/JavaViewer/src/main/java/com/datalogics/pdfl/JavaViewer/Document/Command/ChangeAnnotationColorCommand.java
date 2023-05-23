/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Document.Annotations.AnnotationConsts;
import com.datalogics.pdfl.JavaViewer.Document.Annotations.AnnotationProperties;
import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;
import com.datalogics.pdfl.JavaViewer.Views.Interfaces.ColorPicker;

/**
 * ChangeAnnotationColorCommand - allows to change annotation's
 * foreground/background color.
 */
public class ChangeAnnotationColorCommand extends UpdatePropertyCommand {

    @Override
    protected void prepare() throws FinalState {
        final AnnotationProperties props = ((ApplicationController) getApplication()).getActiveProps();
        colors = getApplication().getView().getDialogs().colorPickerDialog(new ColorPicker(props.getSubtype().equals(AnnotationConsts.Subtypes.FREETEXT) ? props.getTextColor() : props.getForeColor(), props.getInteriorColor()));
        super.prepare();
    }

    @Override
    protected void doInner() throws FinalState {
        final AnnotationProperties props = ((ApplicationController) getApplication()).getActiveProps();

        if (props.getSubtype().equals(AnnotationConsts.Subtypes.FREETEXT)) {
            props.setTextColor(colors.foreColor);
        } else {
            props.setForeColor(colors.foreColor);
        }
        props.setInteriorColor(colors.backColor);
        super.doInner();
    }

    private final static CommandType type = CommandType.EDIT;

    private ColorPicker colors;
}
