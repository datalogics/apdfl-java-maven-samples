/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

/**
 * CustomEditComand - the command which is used for complex annotation
 * construction phase (relevant for PDFL.LinkAnnotation or
 * PDFL.FreeTextAnnotation).
 */
public class CustomEditCommand extends EditAnnotationCommand {
    private final static CommandType type = CommandType.EDIT;
}
