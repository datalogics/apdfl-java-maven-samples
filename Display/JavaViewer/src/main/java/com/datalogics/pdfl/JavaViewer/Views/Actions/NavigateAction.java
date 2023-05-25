/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;
import com.datalogics.pdfl.JavaViewer.Document.Command.NavigateCommand;
import com.datalogics.pdfl.JavaViewer.Presentation.Enums.NavigateMode;

public class NavigateAction extends SimpleAction {
    public NavigateAction() {
        this.fromControl = true;
        this.navigateMode = NavigateMode.NAVIGATE_DIRECT;
    }

    public NavigateAction(NavigateMode navigateMode) {
        this.fromControl = false;
        this.navigateMode = navigateMode;
    }

    @Override
    protected Class<? extends DocumentCommand> getMainCommand() {
        return NavigateCommand.class;
    }

    public void actionPerformed(ActionEvent event) {
        if (fromControl) {
            try {
                if (event.getSource() instanceof JTextField)
                    pageNum = Integer.parseInt(((JTextField) event.getSource()).getText());
                pageNum -= 1; // shift one page as it starts from 0 in the
                              // document
            } catch (NumberFormatException e) {
                pageNum = -1;
            }
        }

        final InvokeParams[] parameters = new InvokeParams[] { new InvokeParams(NavigateMode.class, navigateMode), new InvokeParams(int.class, pageNum) };
        executeMainCommand(parameters);
    }

    private boolean fromControl;
    private NavigateMode navigateMode;
    private int pageNum = -1;
}
