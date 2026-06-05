/*
 * Copyright (C) 2011-2026, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Interfaces;

public interface LinkTargetDialog {
    public boolean isCustom();

    public boolean isZoom();

    public boolean isPosition();

    public boolean isPageNumber();

    public void cancel();
}
