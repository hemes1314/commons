package org.keith.commons.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {
    public static final String BASEURI = "D:\\mnt\\aps\\tmp\\fileUpload\\file\\html";
    public static final String TARGET = "target/results/ch01/";
    public static final String DEST = String.format("%stest.pdf", TARGET);

    public static void main(String[] args) throws IOException {
        File file = new File(TARGET);
        file.mkdirs();

        StringBuilder HTML = new StringBuilder();
        HTML.append("<h4 style='margin:5px;'>模型评估：image-classification-GoogleNet</h4>");
        HTML.append("<img style='display:block;' src=\"img/1.png\">");//style='display:block;margin-bottom:5px;'
        HTML.append("<img style='display:block;' src=\"img/1.png\">");
        HTML.append("<img style='display:block;' src=\"img/1.png\">");

        new Test().createPdf(BASEURI, HTML.toString(), DEST);
        System.out.println("ok");
    }

    /**
     * Creates the PDF file.
     */
    public void createPdf(String baseUri, String html, String dest) throws IOException {
        // 设置字体（得在资源文件resources/fonts中加入字体文件，微软雅黑、宋体等）
        String fonts = this.getClass().getResource("/fonts").getPath();
        System.out.println(fonts);
        FontProvider fontProvider = new DefaultFontProvider();
        fontProvider.addDirectory(fonts);

        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri(baseUri);
        properties.setFontProvider(fontProvider);
        properties.setCharset("UTF-8");
        File pdfFile = new File(dest);
        HtmlConverter.convertToPdf(html, new PdfWriter(pdfFile), properties);
    }
}
