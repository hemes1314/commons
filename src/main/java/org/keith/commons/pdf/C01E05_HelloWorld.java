package org.keith.commons.pdf;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;

/**
 * Converts a simple HTML file to PDF using an InputStream and a PdfWriter
 */
public class C01E05_HelloWorld {

    public static final String BASEURI = "src/main/resources/html/";
    public static final String SRC = String.format("%shello.html", BASEURI);
    public static final String TARGET = "target/results/ch01/";
    public static final String DEST = String.format("%stest-05.pdf", TARGET);

    public static void main(String[] args) throws IOException {
        File file = new File(TARGET);
        file.mkdirs();
        new C01E05_HelloWorld().createPdf(BASEURI, SRC, DEST);
    }

    /**
     * Creates the PDF file. output using PdfWriter
     */
    public void createPdf(String baseUri, String src, String dest) throws IOException {
        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri(baseUri);
        PdfWriter writer = new PdfWriter(dest, new WriterProperties().setFullCompressionMode(true));
        HtmlConverter.convertToPdf(new FileInputStream(src), writer, properties);
    }
}
