package com.datalogics.pdfl.samples;

import java.util.EnumSet;

import com.datalogics.PDFL.Document;
import com.datalogics.PDFL.Library;
import com.datalogics.PDFL.SaveFlags;
import com.datalogics.PDFL.WebConvertInfo;
import com.datalogics.PDFL.WebPageOrientation;
import com.datalogics.PDFL.WebPageSize;
import com.datalogics.PDFL.WebToPDFConvertParams;
import com.datalogics.PDFL.WebViewportPreset;

/*
 * This sample demonstrates converting a web page or a local HTML file into a
 * PDF document.
 *
 * The optional first argument may be a URL (http://, https://, or file://) or a
 * path to a local HTML file.  The sample detects which from the scheme prefix
 * and calls Document.fromWebUrl or Document.fromHtmlFile accordingly.  Relative
 * asset references in a local file resolve against that file's directory.  The
 * optional second argument names the output file.
 *
 * Rendering is performed by the WebToPDF plugin. The plugin runtime ships as a
 * separate add-on package; see pom.xml, which declares it and unpacks it
 * alongside the JNI libraries.
 *
 * Copyright (c) 2026, Datalogics, Inc. All rights reserved.
 *
 */

public class CreateDocFromWebPage {

    /**
     * Returns true if the argument looks like a URL the conversion plugin
     * handles natively, rather than a local filesystem path.
     */
    private static boolean looksLikeUrl(String s) {
        return s.startsWith("http://") || s.startsWith("https://") || s.startsWith("file://");
    }

    /**
     * @param args optional source (URL or HTML file path) and output PDF path
     */
    public static void main(String[] args) throws Throwable {
        System.out.println("CreateDocFromWebPage sample:");

        Library lib = new Library();

        try {
            String source = args.length > 0 ? args[0] : "https://www.datalogics.com";
            String output = args.length > 1 ? args[1] : "CreateDocFromWebPage-out.pdf";

            // Conversion parameters
            WebToPDFConvertParams params = new WebToPDFConvertParams();
            params.setViewportSize(WebViewportPreset.DESKTOP);
            params.setPageSize(WebPageSize.LETTER);
            params.setPageOrientation(WebPageOrientation.PORTRAIT);
            params.setMargins(0.5, 0.5, 0.5, 0.5);
            params.setPrintBackground(true);
            params.setTimeoutSeconds(60);    // 0 uses the 300s plugin default; -1 waits forever

            WebConvertInfo info = new WebConvertInfo();

            final boolean isUrl = looksLikeUrl(source);
            System.out.println("Converting " + (isUrl ? "URL " : "HTML file ") + source + " ...");

            Document doc = isUrl
                    ? Document.fromWebUrl(source, params, info)
                    : Document.fromHtmlFile(source, params, info);

            System.out.println("Wrote " + info.getPageCount() + " pages in "
                    + info.getConversionTimeMs() + " ms");
            if (!info.getTitle().isEmpty()) {
                System.out.println("Title: " + info.getTitle());
            }
            // getSourceUrl reports the URL actually rendered, so it reflects any
            // redirects the browser followed.
            if (!info.getSourceUrl().isEmpty() && !info.getSourceUrl().equals(source)) {
                System.out.println("Resolved URL: " + info.getSourceUrl());
            }

            System.out.println("Saving the document...");
            doc.save(EnumSet.of(SaveFlags.FULL), output);
            System.out.println("Saved to " + output);
        }
        catch (RuntimeException e) {
            System.err.println("Conversion failed: " + e.getMessage());
            System.err.println("If the plugin could not be loaded, confirm that the web "
                    + "conversion runtime was unpacked into target/lib (see pom.xml).");
            throw e;
        }
        finally {
            lib.delete();
        }
    }
}
