/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import com.datalogics.pdfl.JavaViewer.Document.JavaDocument;
import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;
import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;
import com.datalogics.pdfl.JavaViewer.Presentation.Enums.ApplicationMode;

public class ApplicationModeAction extends SimpleAction {
    public ApplicationModeAction() {
    }

    public ApplicationModeAction(ApplicationMode mode) {
        this.mode = mode;
    }

    public void actionPerformed(ActionEvent e) {
        ((ApplicationController) getApplication()).setMode(getCurrentMode());
    }

    @Override
    public boolean isPermitted() {
        ApplicationMode mode = getCurrentMode();
        return JavaDocument.isCommandPermitted(getApplication().getActiveDocument(), mode.getCommandType());
    }

    @Override
    protected Class<? extends DocumentCommand> getMainCommand() {
        return null;
    }

    private ApplicationMode getCurrentMode() {
        ApplicationMode mode = (this.mode == null) ? ((ApplicationController) getApplication()).getDefaultMode() : this.mode;
        return mode;
    }

    private ApplicationMode mode;
}
