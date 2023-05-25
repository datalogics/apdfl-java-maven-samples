/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Interfaces;

import java.awt.Color;

public class ColorPicker {
    public ColorPicker(Color foreColor, Color backColor) {
        this.foreColor = foreColor;
        this.backColor = backColor;
    }

    public void swap() {
        Color tmp = foreColor;
        foreColor = backColor;
        backColor = tmp;
    }

    public Color foreColor;
    public Color backColor;
}
