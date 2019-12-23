package org.keith.commons.pdf;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

public class C01E02_HelloWorld {

    public static final String BASEURI = "src/main/resources/html/";
    public static final String HTML = "<h1>Test</h1><p>Hello World</p><img src=\"img/1.png\">";
    public static final String TARGET = "target/results/ch01/";
    public static final String DEST = String.format("%stest-02.pdf", TARGET);

    public static void main(String[] args) throws IOException {
        File file = new File(TARGET);
        file.mkdirs();
        new C01E02_HelloWorld().createPdf(BASEURI, HTML, DEST);
    }

    /**
     * Creates the PDF file.
     */
    public void createPdf(String baseUri, String html, String dest) throws IOException {
        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri(baseUri);
        HtmlConverter.convertToPdf(html, new FileOutputStream(dest), properties);
    }
}