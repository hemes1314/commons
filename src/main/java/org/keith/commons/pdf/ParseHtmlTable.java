package org.keith.commons.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.net.FileRetrieve;
import com.itextpdf.tool.xml.net.ReadingProcessor;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * html 转换成 pdf
 */
public class ParseHtmlTable {

    public static final String pdfDestPath = "target/results/ch01/";
    public static final String htmlPath = "D:/file/1.htm";

    public static void main(String[] args) throws IOException {
        String pdfName = "test-02.pdf";
        ParseHtmlTable parseHtmlTable = new ParseHtmlTable();
        String htmlStr = FileUtils.readFileToString(new File(htmlPath));
        parseHtmlTable.html2pdf(htmlStr,pdfName ,"/fonts/simsun.ttf");
    }


    public void html2pdf(String html, String pdfName, String fontPath) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = (ITextFontResolver) renderer.getSharedContext().getFontResolver();
            //添加字体库 begin
            fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //添加字体库end
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();
            byte[] buff = os.toByteArray();
            //保存到磁盘上
            FileUtil.byte2File(buff,pdfDestPath,pdfName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}