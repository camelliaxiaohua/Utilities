package camellia.utilities.utils.pdf.electronic;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @Datetime: 2024/11/7下午4:50
 * @author: Camellia.xioahua
 */
public class PdfElectronic {


    public static ByteArrayOutputStream addSeal(ByteArrayOutputStream pdf) {
        PdfReader reader = null;
        ByteArrayOutputStream outputStream = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(new ByteArrayInputStream(pdf.toByteArray()));
            outputStream = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, outputStream);
            // 添加公章
            Image sealImage = Image.getInstance("E:\\workspace\\camellia\\Utilities\\src\\main\\resources\\imags\\seal.png");
            sealImage.setAbsolutePosition(50, 50); // 设置公章的位置
            sealImage.scaleToFit(150, 150); // 设置公章的大小
            PdfContentByte overContent = stamper.getOverContent(1); // 获取第一页的内容层
            overContent.addImage(sealImage);
            return outputStream;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            if (stamper != null) try { stamper.close(); } catch (Exception e) { e.printStackTrace(); }
            if (reader != null)  try { reader.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return outputStream;
    }


    public static ByteArrayOutputStream addSignature(ByteArrayOutputStream pdf) {
        PdfReader reader = null;
        ByteArrayOutputStream outputStream = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(new ByteArrayInputStream(pdf.toByteArray()));
            outputStream = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, outputStream);
            // 添加电子签名图片
            Image signatureImage = Image.getInstance("E:/workspace/camellia/Utilities/src/main/resources/imags/signature.png");
            signatureImage.setAbsolutePosition(400, 50); // 设置电子签名的位置
            signatureImage.scaleToFit(200, 100); // 设置电子签名的大小
            PdfContentByte overContent = stamper.getOverContent(2); // 获取第一页的内容层
            overContent.addImage(signatureImage);
            return outputStream;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            if (stamper != null) try { stamper.close(); } catch (Exception e) { e.printStackTrace(); }
            if (reader != null)  try { reader.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return outputStream;
    }


}
