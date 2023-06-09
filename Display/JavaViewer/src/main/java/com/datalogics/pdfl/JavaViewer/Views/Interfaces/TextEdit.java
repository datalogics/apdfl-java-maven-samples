/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Interfaces;

import java.awt.Color;
import java.awt.Rectangle;

import com.datalogics.PDFL.HorizontalAlignment;

public interface TextEdit {
    void removeTextEdit();

    void processInput(InputData input);

    Rectangle getViewBounds();

    void setViewBounds(Rectangle bound);

    String getText();

    void setText(String text);

    void setBgColor(Color bgColor);

    void setFontColor(Color color);

    void setFontSize(int fontSize);

    void setFontFace(String fontFace);

    void setFontAlign(HorizontalAlignment align);
}
