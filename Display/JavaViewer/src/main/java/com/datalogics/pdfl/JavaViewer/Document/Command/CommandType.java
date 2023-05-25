/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import com.datalogics.PDFL.PermissionRequestOperation;

/**
 * CommandType - enum which contains a command type flags.
 * 
 * This enum kind of wrapper for PDFL.PermissionRequestOperation. It restricts
 * usage of command types depending on PermissionRequestOperation flag.
 */
public enum CommandType {
    OPEN(PermissionRequestOperation.OPEN),
    SAVE(PermissionRequestOperation.FULL_SAVE),
    PRINT(PermissionRequestOperation.PRINT_LOW),
    VIEW(PermissionRequestOperation.OPEN),
    EDIT(PermissionRequestOperation.MODIFY),
    UNDEF(PermissionRequestOperation.UNKNOWN_OPERATION);

    CommandType(PermissionRequestOperation pdfPermission) {
        this.pdfPermission = pdfPermission;
    }

    public PermissionRequestOperation getPermission() {
        return pdfPermission;
    }

    private PermissionRequestOperation pdfPermission;
}
