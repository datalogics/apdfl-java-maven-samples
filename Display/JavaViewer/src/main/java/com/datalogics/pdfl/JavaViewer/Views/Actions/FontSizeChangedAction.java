/*
 * Copyright (C) 2011-2017, Datalogics, Inc. All rights reserved.
 * 
 * For complete copyright information, refer to:
 * http://dev.datalogics.com/adobe-pdf-library/license-for-downloaded-pdf-samples/
 *
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import com.datalogics.pdfl.JavaViewer.Document.JavaDocument;
import com.datalogics.pdfl.JavaViewer.Document.Command.ChangeFontSizeCommand;
import com.datalogics.pdfl.JavaViewer.Document.Command.CommandType;
import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;

public class FontSizeChangedAction extends SimpleAction {
    public void actionPerformed(ActionEvent e) {
        executeMainCommand(new InvokeParams[] { new InvokeParams(int.class, Integer.valueOf(e.getActionCommand())) });
    }

    @Override
    public boolean isPermitted() {
        return JavaDocument.isCommandPermitted(getApplication().getActiveDocument(), CommandType.EDIT);
    }

    @Override
    protected Class<? extends DocumentCommand> getMainCommand() {
        return ChangeFontSizeCommand.class;
    }
}
