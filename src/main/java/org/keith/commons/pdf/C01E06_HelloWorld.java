package org.keith.commons.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

/**
 * Converts a simple HTML file to PDF using an InputStream and a PdfDocument
 */
public class C01E06_HelloWorld {

    public static final String BASEURI = "src/main/resources/html/";
    public static final String SRC = String.format("%shello.html", BASEURI);
    public static final String TARGET = "target/results/ch01/";
    public static final String DEST = String.format("%stest-06.pdf", TARGET);

    public static void main(String[] args) throws IOException {
        File file = new File(TARGET);
        file.mkdirs();
        new C01E06_HelloWorld().createPdf(BASEURI, SRC, DEST);
    }

    /**
     * Creates the PDF file.
     */
    public void createPdf(String baseUri, String src, String dest) throws IOException {
        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri(baseUri);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setTagged();		//用于增加目录
        HtmlConverter.convertToPdf(new FileInputStream(src), pdf, properties);
    }
}
