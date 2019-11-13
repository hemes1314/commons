package org.keith.commons.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

/**
 * Converts a simple HTML file to PDF using an InputStream and an OutputStream
 */
public class C01E04_HelloWorld {

    public static final String BASEURI = "src/main/resources/html/";
    public static final String SRC = String.format("%shello.html", BASEURI);
    public static final String TARGET = "target/results/ch01/";
    public static final String DEST = String.format("%stest-04.pdf", TARGET);

    /**
     * The main method of this example.
     */
    public static void main(String[] args) throws IOException {
        File file = new File(TARGET);
        file.mkdirs();
        new C01E04_HelloWorld().createPdf(BASEURI, SRC, DEST);
    }

    /**
     * Creates the PDF file.
     */
    public void createPdf(String baseUri, String src, String dest) throws IOException {
        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri(baseUri);
        HtmlConverter.convertToPdf(new FileInputStream(src), new FileOutputStream(dest), properties);
    }

}
