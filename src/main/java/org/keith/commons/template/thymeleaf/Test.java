package org.keith.commons.template.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {


        final String TEMPLATE_PREFIX = "templates/";
        final String TEMPLATE_SUFFIX = ".tpl";

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix(TEMPLATE_PREFIX);
        resolver.setSuffix(TEMPLATE_SUFFIX);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        List<ImageData> images = new ArrayList<>();
        images.add(new ImageData("评估子标题", "img/1.png"));
        images.add(new ImageData("评估子标题", "img/2.png"));
        //填充数据
        Context context = new Context();
        context.setVariable("title", "模型评估：image-classification-GoogleNet");
        context.setVariable("images", images);
        //渲染模板生成静态
        FileWriter writer = new FileWriter("D:/mnt/aps/tmp/fileUpload/file/template/index.tpl");
        templateEngine.process("temp", context, writer);
    }
}
