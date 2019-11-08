package org.keith.commons.pdf;


import java.io.File;
import java.io.IOException;

import com.itextpdf.html2pdf.HtmlConverter;

/**
 * Converts a simple HTML file to PDF using File objects
 */
public class C01E03_HelloWorld {

    public static final String BASEURI = "src/main/resources/html/";
    public static final String SRC = String.format("%shello.html", BASEURI);
    public static final String TARGET = "target/results/ch01/";
    public static final String DEST = String.format("%stest-03.pdf", TARGET);

    public static void main(String[] args) throws IOException {
        File file = new File(TARGET);
        file.mkdirs();
        new C01E03_HelloWorld().createPdf(BASEURI, SRC, DEST);
    }

    /**
     * Creates the PDF file.
     * @param baseUri the base URI
     * @param src the path to the source HTML file
     * @param dest the path to the resulting PDF
     */
    public void createPdf(String baseUri, String src, String dest) throws IOException {
        HtmlConverter.convertToPdf(new File(src), new File(dest));
    }
}
