package camellia.utilities.utils.pdf.html;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @Datetime: 2024/11/7下午4:24
 * @author: Camellia.xioahua
 */
public class HtmlConvertPdf {


    public static ByteArrayOutputStream htmlConvertPdf(String html){
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        try {
            ITextRenderer iTextRenderer = new ITextRenderer();
            iTextRenderer.setDocumentFromString(html);
            // 设置中文字体路径
            String fontPath = "font/simsun.ttc";
            // 将字体设置为渲染器的字体
            iTextRenderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            iTextRenderer.layout();
            iTextRenderer.createPDF(pdfStream);
            return pdfStream;
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (pdfStream != null) try {  pdfStream.close(); } catch (IOException e) {e.printStackTrace();}
        }
    }

}
