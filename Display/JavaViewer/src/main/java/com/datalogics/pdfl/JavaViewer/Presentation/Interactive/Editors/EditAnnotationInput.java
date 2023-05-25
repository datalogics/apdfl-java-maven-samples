/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive.Editors;

import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;
import com.datalogics.pdfl.JavaViewer.Document.Command.EditAnnotationCommand;
import com.datalogics.pdfl.JavaViewer.Document.Command.EditGroupCommand;
import com.datalogics.pdfl.JavaViewer.Presentation.Interactive.EditorStates;
import com.datalogics.pdfl.JavaViewer.Views.Interfaces.InputData;

/**
 * EditAnnotationIput - this class is indented for annotation editing.
 * 
 * It generates an EditCommand when annotation is modified and changes editor state.
 */
public class EditAnnotationInput extends BaseAnnotationInput {
    @Override
    public void mousePressed(InputData input) {
        super.mousePressed(input);
        lastHit = getAnnotationEditor().testHit(input.getLocation());
    }

    @Override
    public void mouseMoved(InputData input) {
        super.mouseMoved(input);

        if (lastHit.hasHit()) {
            getAnnotationEditor().move(lastHit, input.getLocation());
            input.markProcessed();
        }
    }

    @Override
    public void mouseReleased(InputData input) {
        super.mouseReleased(input);
        if (lastHit.hasHit()) {
            getAnnotationEditor().setState(EditorStates.SELECT);
            finishEdit();
            lastHit = new Hit();
            input.markProcessed();
        }
    }

    @Override
    protected void onActivate() {
        super.onActivate();
        editCommand = (getAnnotationEditor() instanceof GroupAnnotationEditor) ? new EditGroupCommand() : new EditAnnotationCommand();
        editCommand = getAnnotationEditor().getInteractiveContext().getApplication().executeCommand(editCommand);
    }

    @Override
    protected void onDeactivate() {
        finishEdit();
        super.onDeactivate();
    }

    private void finishEdit() {
        if (editCommand != null) {
            getAnnotationEditor().getInteractiveContext().getApplication().executeCommand(editCommand);
            editCommand = null;
        }
    }

    private Hit lastHit = new Hit();
    private DocumentCommand editCommand;
}
