/*
 * Copyright (C) 2011-2026, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Interfaces;

public interface Layers {
    public void setLayersVisible(boolean visible);

    public void updateLayer(Object layerInfo);

    public void clearLayers();
}
