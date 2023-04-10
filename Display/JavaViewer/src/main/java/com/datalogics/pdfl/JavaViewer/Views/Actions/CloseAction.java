/*
 * Copyright (C) 2011-2017, Datalogics, Inc. All rights reserved.
 * 
 * For complete copyright information, refer to:
 * http://dev.datalogics.com/adobe-pdf-library/license-for-downloaded-pdf-samples/
 *
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import com.datalogics.pdfl.JavaViewer.Document.Command.CloseCommand;
import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;

public class CloseAction extends SimpleAction {

    @Override
    protected Class<? extends DocumentCommand> getMainCommand() {
        return CloseCommand.class;
    }

    public void actionPerformed(ActionEvent e) {
        executeMainCommand(null);
    }
}
