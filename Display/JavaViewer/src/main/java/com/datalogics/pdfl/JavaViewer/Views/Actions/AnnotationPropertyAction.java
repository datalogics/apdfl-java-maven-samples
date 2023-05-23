/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import com.datalogics.pdfl.JavaViewer.Document.JavaDocument;
import com.datalogics.pdfl.JavaViewer.Document.Annotations.AnnotationConsts;
import com.datalogics.pdfl.JavaViewer.Document.Annotations.AnnotationProperties;
import com.datalogics.pdfl.JavaViewer.Document.Command.CommandType;
import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;
import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;
import com.datalogics.pdfl.JavaViewer.Views.Interfaces.PropertyDialog;

public class AnnotationPropertyAction extends SimpleAction {
    public void actionPerformed(ActionEvent e) {
        AnnotationProperties props = ((ApplicationController) getApplication()).getActiveProps();
        final boolean editable = !props.getSubtype().equals(AnnotationConsts.Subtypes.FREETEXT);
        PropertyDialog properties = getApplication().getView().getDialogs().propertyDialog(props.getTitle(), props.getContents(), editable);
        props.setTitle(properties.title);
        props.setContents(properties.content);
    }

    @Override
    public boolean isPermitted() {
        return JavaDocument.isCommandPermitted(getApplication().getActiveDocument(), CommandType.EDIT);
    }

    @Override
    protected Class<? extends DocumentCommand> getMainCommand() {
        return null;
    }
}
