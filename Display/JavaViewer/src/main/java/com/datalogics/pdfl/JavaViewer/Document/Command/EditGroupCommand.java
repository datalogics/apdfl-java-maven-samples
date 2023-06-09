/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

/**
 * EditGroupCommand - allows to save the annotation's properties (except of
 * resizing) for group annotation during interaction with the user.
 */
public class EditGroupCommand extends BaseGroupCommand {
    @Override
    protected void redoInner() throws FinalState {
        super.redoInner();
        changeProperties();
    }

    @Override
    protected void undoInner() throws FinalState {
        super.undoInner();
        changeProperties();
    }

    private final static CommandType type = CommandType.EDIT;
}
