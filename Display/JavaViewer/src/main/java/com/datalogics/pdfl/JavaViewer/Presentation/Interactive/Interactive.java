/*
 * Copyright (C) 2011-2017, Datalogics, Inc. All rights reserved.
 * 
 * For complete copyright information, refer to:
 * http://dev.datalogics.com/adobe-pdf-library/license-for-downloaded-pdf-samples/
 *
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Interactive;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.datalogics.pdfl.JavaViewer.Presentation.Application;
import com.datalogics.pdfl.JavaViewer.Presentation.Cache.PageModel;
import com.datalogics.pdfl.JavaViewer.Views.Interfaces.InputData;
import com.datalogics.pdfl.JavaViewer.Views.Interfaces.PDF;

/**
 * Interactive - defines basic interactive input-output support within the
 * application.
 */
public interface Interactive {
    /***
     * Manages current interactive state of the application
     */
    public static interface InteractiveContext {
        Application getApplication();

        PDF getPdfView();

        PageModel getPageModel();

        /**
         * Methods for high-level cursor change. Manage multiple changes from
         * different sources and maintain correct cursor chain
         */
        void changeCursor(Interactive handler, PDF.Cursor cursor);

        void revertCursor(Interactive handler);
    }

    void mousePressed(InputData input);

    void mouseReleased(InputData input);

    void mouseMoved(InputData input);

    void mouseDoubleClicked(InputData input);

    void keyPressed(InputData input);

    void keyReleased(InputData input);

    void keyTyped(InputData input);

    void draw(Graphics g, Rectangle updateRect);

    void setInteractiveContext(InteractiveContext context);
}
