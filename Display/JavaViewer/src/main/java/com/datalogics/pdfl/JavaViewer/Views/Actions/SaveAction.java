/*
 * Copyright (C) 2011-2017, Datalogics, Inc. All rights reserved.
 * 
 * For complete copyright information, refer to:
 * http://dev.datalogics.com/adobe-pdf-library/license-for-downloaded-pdf-samples/
 *
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;
import com.datalogics.pdfl.JavaViewer.Document.Command.SaveCommand;

public class SaveAction extends SimpleAction {
    public SaveAction(boolean saveAs) {
        saveMode = saveAs ? SaveCommand.SaveMode.SAVE_AS : SaveCommand.SaveMode.SILENT_SAVE;
    }

    @Override
    protected Class<? extends DocumentCommand> getMainCommand() {
        return SaveCommand.class;
    }

    public void actionPerformed(ActionEvent e) {
        final InvokeParams[] parameters = new InvokeParams[] { new InvokeParams(SaveCommand.SaveMode.class, saveMode) };
        executeMainCommand(parameters);
    }

    private SaveCommand.SaveMode saveMode;
}
