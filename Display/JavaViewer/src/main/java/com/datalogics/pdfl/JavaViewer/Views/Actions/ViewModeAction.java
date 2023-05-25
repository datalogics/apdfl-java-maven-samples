/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;
import com.datalogics.pdfl.JavaViewer.Document.Command.ViewModeCommand;
import com.datalogics.pdfl.JavaViewer.Presentation.Enums.PageViewMode;

public class ViewModeAction extends SimpleAction {
    public ViewModeAction(PageViewMode viewMode) {
        this.viewMode = viewMode;
    }

    @Override
    protected Class<? extends DocumentCommand> getMainCommand() {
        return ViewModeCommand.class;
    }

    public void actionPerformed(ActionEvent e) {
        final InvokeParams[] parameters = new InvokeParams[] { new InvokeParams(PageViewMode.class, viewMode) };
        executeMainCommand(parameters);
    }

    private PageViewMode viewMode;
}
