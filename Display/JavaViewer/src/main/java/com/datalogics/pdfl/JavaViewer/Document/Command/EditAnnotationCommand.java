/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

/**
 * EditAnnotationCommand - allows to save the annotation's properties during
 * interaction with the user.
 */
public class EditAnnotationCommand extends BaseAnnotationCommand implements PendingCommand {
    public boolean consume(Class<? extends DocumentCommand> otherClass) {
        return false;
    }

    @Override
    protected void undoInner() throws FinalState {
        super.undoInner();
        changeProperties();
    }

    @Override
    protected void redoInner() throws FinalState {
        super.redoInner();
        changeProperties();
    }

    private final static CommandType type = CommandType.EDIT;
}
