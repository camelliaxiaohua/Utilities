package camellia.utilities.utils.pdf.html;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @Datetime: 2024/11/7下午4:24
 * @author: Camellia.xiaohua
 */
public class HtmlConvertPdf {

    /**
     * 将给定的 HTML 字符串转换成 PDF 格式，并输出到 ByteArrayOutputStream 中。
     * @author: Camellia.xiaohua
     * @Datetime: 2024/11/8
     * @param html 要转换成 PDF 的 HTML 字符串。
     * @param fontPath 用于支持中文显示的字体文件路径，必须提供有效的字体路径。
     * @return 包含生成的 PDF 内容的 ByteArrayOutputStream 对象。
     * @throws IllegalArgumentException 如果提供的字体路径为空或无效。
     * @throws RuntimeException 如果在转换过程中发生任何错误，如文档处理异常或输入/输出异常。
     */
    public static ByteArrayOutputStream htmlConvertPdf(String html, String fontPath) {
        if (fontPath == null || fontPath.isEmpty()) {
            throw new IllegalArgumentException("字体路径不能为空。");
        }
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        ITextRenderer iTextRenderer = new ITextRenderer();
        try {
            // 设置HTML文档
            iTextRenderer.setDocumentFromString(html);
            // 设置中文字体路径
            iTextRenderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // 布局渲染
            iTextRenderer.layout();
            // 生成PDF
            iTextRenderer.createPDF(pdfStream);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("PDF转换过程中发生错误: " + e.getMessage(), e);
        }
        return pdfStream;
    }


}
