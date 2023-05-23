/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Views.Actions;

import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractAction;

import com.datalogics.pdfl.JavaViewer.Document.JavaDocument;
import com.datalogics.pdfl.JavaViewer.Document.Command.DocumentCommand;
import com.datalogics.pdfl.JavaViewer.Presentation.Application;

public abstract class SimpleAction extends AbstractAction {
    public void setApplication(Application app) {
        this.application = app;
    }

    public boolean isPermitted() {
        return JavaDocument.isCommandPermitted(getApplication().getActiveDocument(), getMainCommand());
    }

    protected abstract Class<? extends DocumentCommand> getMainCommand();

    protected Application getApplication() {
        return this.application;
    }

    protected void executeMainCommand(InvokeParams[] parameters) {
        Class<?>[] paramTypes = null;
        Object[] params = null;

        if (parameters != null) {
            paramTypes = new Class<?>[parameters.length];
            for (int i = 0; i < parameters.length; ++i)
                paramTypes[i] = parameters[i].type;

            params = new Object[parameters.length];
            for (int i = 0; i < parameters.length; ++i)
                params[i] = parameters[i].value;

        }

        DocumentCommand command = null;
        try {
            command = getMainCommand().getConstructor(paramTypes).newInstance(params);
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalArgumentException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        getApplication().executeCommand(command);
    }

    protected static class InvokeParams {
        public InvokeParams(Class<?> type, Object value) {
            this.type = type;
            this.value = value;
        }

        public Class<?> type;
        public Object value;
    }

    public static final String SELECTED = "SELECTED";

    private Application application;
}
