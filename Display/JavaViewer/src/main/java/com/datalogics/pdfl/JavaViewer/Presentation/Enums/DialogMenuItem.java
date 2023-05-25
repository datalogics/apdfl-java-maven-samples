/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Enums;

/**
 * DialogMenuItem - represents menu items from presentation.
 */
public enum DialogMenuItem {
    COMPLETE("Complete"),
    CANCEL("Cancel");

    private DialogMenuItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;
}
