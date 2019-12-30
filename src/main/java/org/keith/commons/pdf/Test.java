package org.keith.commons.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.font.FontProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Test {
    public static final String BASEURI = "D:/mnt/aps/tmp/fileUpload/file/template/";
    public static final String SRC = String.format("%stemp.tpl", BASEURI);
    public static final String TARGET = "target/results/ch01/";
    public static final String DEST = String.format("%stest.pdf", TARGET);

    public static void main(String[] args) throws IOException {
        File file = new File(TARGET);
        file.mkdirs();
        File picture = new File("d:/file/1.png");
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
        System.out.println(sourceImg.getWidth());
        StringBuilder HTML = new StringBuilder();
        HTML.append("<HTML>");
        HTML.append("<head>");
        HTML.append("<style type=\"text/css\">");
        HTML.append(" @page{size:210mm 1000mm; }");// width height , A4: 210mm * 297mm
        HTML.append("</style>");
        HTML.append("</head>");
        HTML.append("<body style = \"font-family: SimSun; font-size:small;\">");
        HTML.append("<h3 style='margin:0px;'>模型评估：image-classification-GoogleNet</h3>");
        HTML.append("<h4 style='margin:0px;'>评估子标题</h4>");
//        HTML.append("<img style='display:block;width:100%;' src=\"d:/file/1.png\"/>");//style='display:block;margin-bottom:5px;'
        HTML.append("<table style='table-layout: fixed; word-break: break-all; word-break:break-strict; width: 100%; text-align: center;' border='1' cellspacing='0' cellpadding='2'>");
        for(int i = 0; i < 50; i++) {
            HTML.append("<tr>");
            HTML.append("<td style='width: 17%;'>");HTML.append("x2 * x3");HTML.append("</td>");
            HTML.append("<td style='width: 17%;'>");HTML.append("dc_model_repo.step.sklearn_step.SKLearnDCTransformer");HTML.append("</td>");
            HTML.append("<td style='width: 17%;'>");HTML.append("dc_model_repo.step.sklearn_step.SKLearnDCTransformer");HTML.append("</td>");
            HTML.append("<td style='width: 17%;'>");HTML.append("dc_model_repo.step.sklearn_step.SKLearnDCTransformer");HTML.append("</td>");
            HTML.append("<td>");HTML.append("--");HTML.append("</td>");
            HTML.append("<td>");HTML.append("--");HTML.append("</td>");
            HTML.append("</tr>");
        }
        HTML.append("</table>");
        HTML.append("</body>");
        HTML.append("</HTML>");
        System.out.println(HTML.toString());
        new Test().createPdf(BASEURI, HTML.toString(), DEST);
//        new Test().createPdf(BASEURI, SRC, DEST);
        System.out.println("ok");
    }

    /**
     * Creates the PDF file.
     */
    public void createPdf(String baseUri, String src, String dest) throws IOException {
        // 设置字体（得在资源文件resources/fonts中加入字体文件，微软雅黑、宋体等）
        String fonts = this.getClass().getResource("/fonts").getPath();
        System.out.println(fonts);
        FontProvider fontProvider = new DefaultFontProvider();
        fontProvider.addDirectory(fonts);

        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri(baseUri);
        properties.setFontProvider(fontProvider);
        properties.setCharset("UTF-8");
        PdfWriter pdfWriter = new PdfWriter(dest, new WriterProperties().setFullCompressionMode(false));
        HtmlConverter.convertToPdf(src, pdfWriter, properties);
//        HtmlConverter.convertToPdf(new FileInputStream(src), pdfWriter, properties);
    }
}
