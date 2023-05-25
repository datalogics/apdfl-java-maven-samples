/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Enums;

/**
 * PageViewMode - enum which determines PDF document's paging mode.
 * 
 * @author 1
 * 
 */
public enum PageViewMode {
    PAGE_MODE_NONE,
    PAGE_MODE_SINGLE,
    PAGE_MODE_CONTINUOUS;

    public boolean isSinglePage() {
        return this.equals(PAGE_MODE_SINGLE);
    }

    public boolean isContinuousPage() {
        return this.equals(PAGE_MODE_CONTINUOUS);
    }
}
