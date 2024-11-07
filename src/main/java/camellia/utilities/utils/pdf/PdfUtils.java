package camellia.utilities.utils.pdf;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class PdfUtils {


    /**
     * 将 Spring Model 转换为 Thymeleaf Context
     *
     * @param model Spring 模型
     * @return 转换后的 Thymeleaf Context
     */
    private static Context convertModelToContext(Model model) {
        Context context = new Context();
        model.asMap().forEach(context::setVariable);  // 将模型中的变量复制到 Context 中
        return context;
    }

    /**
     * 渲染 Thymeleaf 模板为 HTML
     *
     * @param model 传递给模板的模型
     * @param templateName Thymeleaf 模板名称
     * @return 渲染后的 HTML 字符串
     */
    public static String renderTemplateToHtml(Model model, String templateName, SpringTemplateEngine templateEngine) {
        Context context = convertModelToContext(model);
        String htmlContent = templateEngine.process(templateName, context);
        if (htmlContent == null || htmlContent.isEmpty()) {
            throw new IllegalArgumentException("HTML content is empty or null");
        }
        return htmlContent;
    }

    /**
     * 将 HTML 内容转换为 PDF
     *
     * @param htmlContent HTML 内容
     * @return PDF 的字节输出流
     * @throws DocumentException
     * @throws IOException
     */
    private static ByteArrayOutputStream convertHtmlToPdf(String htmlContent) throws DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();
        System.out.println("Before setDocument: " + renderer);

        // 确保htmlContent不为空且格式正确
        if (htmlContent == null || htmlContent.isEmpty()) {
            System.err.println("Invalid HTML content.");
            return null;
        }

        try {
            renderer.setDocumentFromString(htmlContent);
        } catch (Exception e) {
            System.err.println("Error while setting document: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        System.out.println("After setDocument: " + renderer);

        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        try {
            renderer.createPDF(pdfStream);
        } catch (Exception e) {
            System.err.println("Error while creating PDF: " + e.getMessage());
            e.printStackTrace();
        }
        return pdfStream;
    }


    /**
     * 将 Thymeleaf 模板渲染成 HTML，然后转换为 PDF
     *
     * @param model 传递给模板的模型
     * @param templateName 模板名称
     * @return PDF 文件的字节数组输出流
     * @throws DocumentException
     * @throws IOException
     */
    public static ByteArrayOutputStream convertHtmlToPdf(Model model, String templateName, SpringTemplateEngine templateEngine)
            throws DocumentException, IOException {
        // 先渲染模板为 HTML
        String htmlContent = renderTemplateToHtml(model, templateName, templateEngine);
        // 然后将 HTML 转换为 PDF
        return convertHtmlToPdf(htmlContent);
    }
}
