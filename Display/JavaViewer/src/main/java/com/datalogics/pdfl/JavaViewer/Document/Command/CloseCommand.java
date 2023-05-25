/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Document.JavaDocument;

/**
 * CloseCommand - the command which allows to close current document.
 * 
 * If document was modified this command proposes to save the document by
 * executing SaveCommand.
 */
public class CloseCommand extends DocumentCommand {
    @Override
    protected void prepare() throws FinalState {
    }

    @Override
    protected void doInner() throws FinalState {
        // try to save the current document
        if (getApplication().executeCommand(new SaveCommand(SaveCommand.SaveMode.CLOSE_SAVE)).getState() == State.Canceled)
            throw new FinalState(State.Canceled);

        JavaDocument document = getApplication().getActiveDocument();
        if (document == null)
            throw new FinalState(State.Failed);

        document.close();
        getApplication().removeDocument(document);
    }

    private final static CommandType type = CommandType.VIEW; // field used through reflection
}
