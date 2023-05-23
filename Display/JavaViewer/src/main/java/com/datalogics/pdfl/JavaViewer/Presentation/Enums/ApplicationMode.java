/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Presentation.Enums;

import com.datalogics.pdfl.JavaViewer.Document.Command.CommandType;

/**
 * ApplicationMode - defines all available application's interactive modes (main
 * modes).
 */
public enum ApplicationMode {
    NONE_MODE(CommandType.OPEN),
    SCROLL_MODE(CommandType.VIEW),
    ZOOM_MODE(CommandType.VIEW),
    MARQUEE_ZOOM_MODE(CommandType.VIEW),
    EDIT_ANNOTATION_MODE(CommandType.EDIT),
    CUSTOM_MODE(CommandType.EDIT);

    ApplicationMode(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    private CommandType commandType;
}
