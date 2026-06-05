/*
 * Copyright (C) 2011-2026, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Interfaces;

public class PropertyDialog {
    public void setProperties(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String title;
    public String content;
}
