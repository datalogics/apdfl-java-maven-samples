/*
 * Copyright (C) 2011-2023, Datalogics, Inc. All rights reserved.
 * 
 */

package com.datalogics.pdfl.JavaViewer.Document.Annotations;

import java.util.EnumSet;

import com.datalogics.PDFL.Annotation;
import com.datalogics.PDFL.Color;
import com.datalogics.PDFL.Content;
import com.datalogics.PDFL.Element;
import com.datalogics.PDFL.Font;
import com.datalogics.PDFL.Form;
import com.datalogics.PDFL.GraphicState;
import com.datalogics.PDFL.HorizontalAlignment;
import com.datalogics.PDFL.Path;
import com.datalogics.PDFL.PathPaintOpFlags;
import com.datalogics.PDFL.Point;
import com.datalogics.PDFL.Rect;
import com.datalogics.PDFL.Text;
import com.datalogics.PDFL.TextRun;
import com.datalogics.PDFL.TextState;
import com.datalogics.pdfl.JavaViewer.Utils;

/**
 * FreeTextAnnotationHolder - the child of the BaseAnnotationHolder.
 * 
 * This class allows to manipulate PDFL.FreeTextAnnotation using specific
 * functionality. It helps generate proper appearance for
 * PDFL.FreeTextAnnotation with regard to system font, justification, border
 * size etc.
 */
public class FreeTextAnnotationHolder extends BaseAnnotationHolder {
    @Override
    protected void init(Annotation annotation, int index, int pageIndex) {
        super.init(annotation, index, pageIndex);
    }

    @Override
    protected void doReadProperties() {
        super.doReadProperties();
        readPropertyByName(AnnotationConsts.TEXT_COLOR, Color.class, false);
        readPropertyByName(AnnotationConsts.INTERIOR_COLOR, Color.class, false);
        readPropertyByName(AnnotationConsts.FONT_FACE, String.class, false);
        readPropertyByName(AnnotationConsts.FONT_SIZE, double.class, false);
        readPropertyByName(AnnotationConsts.QUADDING, HorizontalAlignment.class, false);
        setAutosize();
    }

    private void setAutosize() {
        if (getProperties().getAutoSize()) {
            Content content = getAnnotation().getNormalAppearance().getContent();
            double newBoundHeight = 0;
            for (int i = 0; i < content.getNumElements(); i++) {
                Element element = content.getElement(i);
                if (element instanceof Text) {
                    Text currentText = (Text) element;
                    for (int j = 0; j < currentText.getNumberOfRuns(); j++)
                        newBoundHeight += currentText.getRun(j).getBoundingBox().getHeight();
                }
            }
            getProperties().setBoundingRect(new Rect(getProperties().getBoundingRect().getLLx(), getProperties().getBoundingRect().getURy() - newBoundHeight, getProperties().getBoundingRect().getURx(), getProperties().getBoundingRect().getURy()));
            getProperties().setAutoSize(false);
        }
    }

    /**
     * Method is used to update FreeTextAnnotation's appearance. It sets font
     * face, font size, font alignment and foreground and background colors of
     * annotation.
     * 
     * @param content - the Datalogics Content is from annotation
     * NormalAppearance.
     */
    private void setProperties(Content content) {
        boolean useDefaultFont = false;
        Path path = null;
        TextRun currentTextRun = null;
        GraphicState gState = null;
        Font dlFont = null;
        for (int i = 0; i < content.getNumElements(); i++) {
            Element element = content.getElement(i);
            if (element instanceof Text) {
                Text currentText = (Text) element;
                for (int j = 0; j < currentText.getNumberOfRuns(); j++) {
                    currentTextRun = currentText.getRun(j);
                    dlFont = currentTextRun.getFont();
                    if (dlFont != null)
                        break;
                }
            } else if (element instanceof Path) {
                path = (Path) element;
                gState = path.getGraphicState();
            }
        }

        if (dlFont == null) {
            dlFont = initFont();
            useDefaultFont = true;
        }

        if (gState == null)
            gState = new GraphicState();

        if (currentTextRun == null)
            currentTextRun = new TextRun("", dlFont, gState, new TextState(), new com.datalogics.PDFL.Matrix());

        if (path == null)
            path = new Path(gState);

        String fontName = "";
        for (String name : AnnotationFactory.getAvailableFonts()) {
            String trimmedName = name.replace(" ", "");
            if (name.indexOf(dlFont.getName()) != -1 || trimmedName.indexOf(dlFont.getName()) != -1 || dlFont.getName().indexOf(name) != -1 || dlFont.getName().indexOf(trimmedName) != -1) {
                fontName = name;
                break;
            }
        }

        if (!useDefaultFont) {
            // if we had no content, we'll probably lose text
            // run with custom font parameters, so, use ones
            // that left in the object from previous operations
            getProperties().setFontFace(fontName.length() != 0 ? fontName : "Arial");
            getProperties().setFontSize((int) currentTextRun.getTextState().getFontSize());
        }

        if (gState.getStrokeColor() != null)
            getProperties().setForeColor(Utils.transform(gState.getStrokeColor()));
        if (gState.getFillColor() != null && path.getPaintOp().contains(PathPaintOpFlags.FILL)) /*& PathPaintOpFlags.FILL)*/
            getProperties().setInteriorColor(Utils.transform(gState.getFillColor()));
        else
            getProperties().resetFill();
    }

    private Font initFont() {
        Font dlFont = null;
        try {
            dlFont = new Font(getProperties().getFontFace());
        } catch (RuntimeException e) {
            getProperties().setFontFace(Font.getFontList().get(0).getName());
            dlFont = new Font(getProperties().getFontFace());
        }
        return dlFont;
    }
}
