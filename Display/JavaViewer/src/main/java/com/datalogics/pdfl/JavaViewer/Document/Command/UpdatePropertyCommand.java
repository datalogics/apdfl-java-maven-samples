/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Document.Annotations.AnnotationConsts;
import com.datalogics.pdfl.JavaViewer.Document.Annotations.AnnotationHolder;

/**
 * UpdatePropertyCommand - the base class for commands which change annotation
 * properties.
 * 
 * This command allows to save the annotation's properties before they will be
 * changed.
 */
public class UpdatePropertyCommand extends BaseAnnotationCommand {
    @Override
    protected void prepare() throws FinalState {
        Object selection = getApplication().getActiveDocument().getSelection();
        if (selection != null && selection instanceof AnnotationHolder) {
            AnnotationHolder holder = (AnnotationHolder) selection;
            command = holder.getProperties().getSubtype().equals(AnnotationConsts.Subtypes.GROUP) ? new EditGroupCommand() : new EditAnnotationCommand();

            command.setPDFPresenter(getPDFPresenter());
            command.setAnnotationInteractive(getAnnotationInteractive());
            command.setApplication(getApplication());

            command.saveAnotationParams();
        }
    }

    @Override
    protected void doInner() throws FinalState {
        if (command == null)
            throw new FinalState(State.Failed);

        command.doInner();
    }

    @Override
    protected void redoInner() throws FinalState {
        command.redoInner();
    }

    @Override
    protected void undoInner() throws FinalState {
        command.undoInner();
    }

    private final static CommandType type = CommandType.EDIT;

    private BaseAnnotationCommand command;
}
