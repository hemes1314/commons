package org.keith.commons.pdf;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.html2pdf.HtmlConverter;

/**
 * Converts a simple Hello World HTML String to a PDF document.
 */
public class C01E01_HelloWorld {

    /**
     * The HTML-html原文件路径
     * The target —— 结果的输出所在的文件夹
     * DesT —— pdf输出的具体路径
     */
    public static final String HTML = "<h1>Test</h1><p>Hello World</p>";
    public static final String TARGET = "target/results/ch01/";
    public static final String DEST = String.format("%stest-01.pdf", TARGET);

    public static void main(String[] args) throws IOException {
        File file = new File(TARGET);
        file.mkdirs();
        new C01E01_HelloWorld().createPdf(HTML, DEST);
        System.out.println("ok");
    }

    /**
     * Creates the PDF file.
     */
    public void createPdf(String html, String dest) throws IOException {
        HtmlConverter.convertToPdf(html, new FileOutputStream(dest));
    }
}
