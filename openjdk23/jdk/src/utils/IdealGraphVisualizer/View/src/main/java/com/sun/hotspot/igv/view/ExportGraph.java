/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package com.sun.hotspot.igv.view;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGraphics2D;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;


public class ExportGraph implements ExportCookie {

    @Override
    public void export(File f) {
        EditorTopComponent editor = EditorTopComponent.getActive();
        if (editor != null) {
            String lcFileName = f.getName().toLowerCase();
            if (lcFileName.endsWith(".pdf")) {
                exportToPDF(editor, f);
            } else if (lcFileName.endsWith(".svg")) {
                exportToSVG(editor, f);
            } else {
                NotifyDescriptor message = new NotifyDescriptor.Message("Unknown image file extension: expected either '.pdf' or '.svg'", NotifyDescriptor.ERROR_MESSAGE);
                DialogDisplayer.getDefault().notifyLater(message);
            }
        }
    }

    private static void exportToPDF(EditorTopComponent editor, File f) {
        int width = editor.getSceneBounds().width;
        int height = editor.getSceneBounds().height;
        com.lowagie.text.Document document = new Document(new Rectangle(width, height));
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, Files.newOutputStream(f.toPath()));
            writer.setCloseStream(true);
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            PdfGraphics2D pdfGenerator = new PdfGraphics2D(contentByte, width, height);
            editor.paintScene(pdfGenerator);
            pdfGenerator.dispose();
            contentByte.addTemplate(template, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private static void exportToSVG(EditorTopComponent editor, File f) {
        SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(GenericDOMImplementation.getDOMImplementation()
                .createDocument("http://www.w3.org/2000/svg", "svg", null));
        ctx.setEmbeddedFontsOn(true);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(ctx, true);
        editor.paintScene(svgGenerator);
        try (FileOutputStream os = new FileOutputStream(f)) {
            Writer out = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            svgGenerator.stream(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
