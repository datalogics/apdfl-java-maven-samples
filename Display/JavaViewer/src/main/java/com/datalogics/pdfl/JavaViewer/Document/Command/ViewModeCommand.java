/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Presentation.Enums.PageViewMode;

/**
 * ViewModeCommand - allows to set document's paging type.
 */
public class ViewModeCommand extends ViewCommand {
    public ViewModeCommand(PageViewMode viewMode) {
        this.viewMode = (viewMode != null) ? viewMode : PageViewMode.PAGE_MODE_SINGLE;
    }

    @Override
    protected void prepare() throws FinalState {
    }

    @Override
    protected void doInner() throws FinalState {
        getPDFPresenter().setViewMode(viewMode);
    }

    private final static CommandType type = CommandType.VIEW; // field used through reflection

    private PageViewMode viewMode;
}
