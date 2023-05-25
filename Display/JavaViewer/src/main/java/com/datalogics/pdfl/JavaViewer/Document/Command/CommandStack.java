/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Command;

import java.util.Stack;

/**
 * CommandStack - stores commands into stack.
 * 
 * This class contains prev() and next() methods to provide undo/redo
 * functionality. It also contains StackListener interface which is used to
 * notify its implementers that command stack has been updated.
 */
class CleanableStack extends Stack<DocumentCommand> {
    @Override
    public void clear() {
        for (DocumentCommand command : this) {
            command.cleanup();
        }
        super.clear();
    }
}

public class CommandStack {
    public static interface StackListener {
        void stackUpdated();
    }

    public CommandStack(StackListener listener) {
        this.listener = listener;
        this.done = new CleanableStack();
        this.undone = new CleanableStack();
    }

    public void add(final DocumentCommand command) {
        // clear stack tail if a new operation has been executed
        undone.clear();
        done.push(command);
        listener.stackUpdated();
    }

    public DocumentCommand prev() {
        try {
            return canPrev() ? undone.push(done.pop()) : null;
        } finally {
            listener.stackUpdated();
        }
    }

    public DocumentCommand next() {
        try {
            return canNext() ? done.push(undone.pop()) : null;
        } finally {
            listener.stackUpdated();
        }
    }

    public boolean canPrev() {
        return !done.empty();
    }

    public boolean canNext() {
        return !undone.empty();
    }

    public void clear() {
        done.clear();
        undone.clear();
        listener.stackUpdated();
    }

    private final StackListener listener;
    private final Stack<DocumentCommand> done;
    private final Stack<DocumentCommand> undone;
}
