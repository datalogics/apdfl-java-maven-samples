/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

/**
 * PendingCommand - marker interface which shows that command continues
 * execution until new command comes or user breaks the operation.
 */
public interface PendingCommand {
    /**
     * Checks which command types should be "consumed" during execution of this
     * command and bypass command stack.
     * 
     * @param otherClass Command type to check.
     * @return true if another command type should be consumed.
     */
    boolean consume(Class<? extends DocumentCommand> otherClass);
}
