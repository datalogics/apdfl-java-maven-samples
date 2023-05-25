/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import com.datalogics.pdfl.JavaViewer.Document.JavaDocument;
import com.datalogics.pdfl.JavaViewer.Document.Command.CommandType;
import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;
import com.datalogics.pdfl.JavaViewer.Presentation.ApplicationController;

public class TextSearchAction extends SimpleAction {

    public void actionPerformed(ActionEvent e) {
        if (textField != null) {
            final String seacrhPhrase = textField.getText();
            ((ApplicationController) getApplication()).searchText(seacrhPhrase);
        }
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public boolean isPermitted() {
        return (textField != null) && JavaDocument.isCommandPermitted(getApplication().getActiveDocument(), CommandType.VIEW);
    }

    @Override
    protected Class<? extends DocumentCommand> getMainCommand() {
        return null;
    }

    private JTextField textField;
}
