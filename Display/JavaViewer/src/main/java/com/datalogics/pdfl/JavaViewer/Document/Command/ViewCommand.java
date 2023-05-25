/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.pdfl.JavaViewer.Presentation.PDFPresenter;

/**
 * ViewCommand - the base class for all commands which responsible for the
 * document's representation change.
 * 
 * This command has PDFPresenter object for document view manipulation.
 */
public abstract class ViewCommand extends DocumentCommand {
    public void setPDFPresenter(PDFPresenter presenter) {
        this.pdfPresenter = presenter;
    }

    final protected PDFPresenter getPDFPresenter() {
        return this.pdfPresenter;
    }

    private PDFPresenter pdfPresenter;
}
