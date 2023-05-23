/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;

import com.datalogics.pdfl.JavaViewer.Views.Actions.SimpleAction;

public class SelectionListener implements PropertyChangeListener {

    public SelectionListener(AbstractButton button) {
        this.button = button;
    }

    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().equals(SimpleAction.SELECTED)) {
            button.setSelected(Boolean.TRUE.equals(e.getNewValue()));
        }
    }

    private AbstractButton button;
}
