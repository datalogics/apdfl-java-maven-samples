/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Views.Interfaces.Dialogs;

/**
 * UnlockCommand - show the password dialog specified for the document.
 * 
 * This command passes the password to the JavaDocument object for unlocking the
 * document.
 */
public class UnlockCommand extends DocumentCommand {
    @Override
    protected void prepare() throws FinalState {
        pass = getApplication().getView().getDialogs().promptPassword();
    }

    @Override
    protected void doInner() throws FinalState {
        if (getApplication().getActiveDocument() != null && pass != null) {
            if (!getApplication().getActiveDocument().unlock(pass)) {
                getApplication().getView().getDialogs().messageDialog(Dialogs.MessageType.OPEN_FAIL);
            }
        }
    }

    private final static CommandType type = CommandType.VIEW; // field used through reflection

    private String pass;
}
