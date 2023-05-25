/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
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
