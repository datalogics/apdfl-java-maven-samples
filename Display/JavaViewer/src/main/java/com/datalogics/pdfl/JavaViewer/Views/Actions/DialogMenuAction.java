/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.datalogics.pdfl.JavaViewer.Presentation.Enums.DialogMenuItem;
import com.datalogics.pdfl.JavaViewer.Views.Interfaces.DialogMenuResultNotifier;

public class DialogMenuAction extends AbstractAction {
    public DialogMenuAction(DialogMenuItem item, final DialogMenuResultNotifier subscriber, String name) {
        this.putValue(Action.NAME, name);

        this.subscriber = subscriber;
        this.item = item;
    }

    public void actionPerformed(ActionEvent e) {
        subscriber.dialogMenuResult(item);
    }

    private DialogMenuResultNotifier subscriber;
    private DialogMenuItem item;
}
