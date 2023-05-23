/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import com.datalogics.pdfl.JavaViewer.Document.JavaDocument;
import com.datalogics.pdfl.JavaViewer.Document.Command.ChangeLineWidthCommand;
import com.datalogics.pdfl.JavaViewer.Document.Command.CommandType;
import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;
import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;

public class ChangeLineWidthAction extends SimpleAction {
    public ChangeLineWidthAction(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void actionPerformed(ActionEvent e) {
        final float width = lineWidth == -1 ? getApplication().getView().getDialogs().customLineWidth(0, 100, (int) ((ApplicationController) getApplication()).getActiveProps().getLineWidth()) : lineWidth;
        executeMainCommand(new InvokeParams[] { new InvokeParams(float.class, width) });
    }

    @Override
    public boolean isPermitted() {
        return JavaDocument.isCommandPermitted(getApplication().getActiveDocument(), CommandType.EDIT);
    }

    @Override
    protected Class<? extends DocumentCommand> getMainCommand() {
        return ChangeLineWidthCommand.class;
    }

    private float lineWidth;
}
