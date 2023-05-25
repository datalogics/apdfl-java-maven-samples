/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;
import com.datalogics.pdfl.JavaViewer.Document.Command.OpenCommand;

public class OpenAction extends SimpleAction {
    @Override
    protected Class<? extends DocumentCommand> getMainCommand() {
        return OpenCommand.class;
    }

    public void actionPerformed(ActionEvent e) {
        executeMainCommand(null);
    }
}
