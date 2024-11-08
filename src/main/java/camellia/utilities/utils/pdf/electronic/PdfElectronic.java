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

/**
 * @Datetime: 2024/11/7下午4:50
 * @author: Camellia.xioahua
 */
public class PdfElectronic {



    public static ByteArrayOutputStream addSeal(ByteArrayOutputStream pdf, Integer pageNum, String sealPath) {
        PdfReader reader = null;
        ByteArrayOutputStream outputStream = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(new ByteArrayInputStream(pdf.toByteArray()));
            outputStream = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, outputStream);
            // 添加公章
            Image sealImage = Image.getInstance(sealPath);
            sealImage.setAbsolutePosition(50, 50); // 设置公章的位置
            sealImage.scaleToFit(150, 150); // 设置公章的大小
            PdfContentByte overContent = stamper.getOverContent(pageNum); // 需要在哪一页盖章
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


    public static ByteArrayOutputStream addSeal(ByteArrayOutputStream pdf, String sealPath) {
        PdfReader reader = null;
        ByteArrayOutputStream outputStream = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(new ByteArrayInputStream(pdf.toByteArray()));
            outputStream = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, outputStream);
            // 加载电子章图片
            Image sealImage = Image.getInstance(sealPath);
            sealImage.setAbsolutePosition(50, 50); // 设置公章的位置
            sealImage.scaleToFit(150, 150); // 设置公章的大小
            // 遍历 PDF 的每一页添加公章
            int totalPages = reader.getNumberOfPages();
            for (int i = 1; i <= totalPages; i++) {
                PdfContentByte overContent = stamper.getOverContent(i);
                overContent.addImage(sealImage);
            }
            return outputStream;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            if (stamper != null) try { stamper.close(); } catch (Exception e) { e.printStackTrace(); }
            if (reader != null)  try { reader.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return outputStream;
    }


    public static ByteArrayOutputStream addSignature(ByteArrayOutputStream pdf, Integer pageNum, String signaturePath) {
        PdfReader reader = null;
        ByteArrayOutputStream outputStream = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(new ByteArrayInputStream(pdf.toByteArray()));
            outputStream = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, outputStream);
            // 添加电子签名图片
            Image signatureImage = Image.getInstance(signaturePath);
            signatureImage.setAbsolutePosition(400, 50); // 设置电子签名的位置
            signatureImage.scaleToFit(200, 100); // 设置电子签名的大小
            PdfContentByte overContent = stamper.getOverContent(pageNum); // 获取第一页的内容层
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


    public static ByteArrayOutputStream addSignature(ByteArrayOutputStream pdf, String signaturePath) {
        PdfReader reader = null;
        ByteArrayOutputStream outputStream = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(new ByteArrayInputStream(pdf.toByteArray()));
            outputStream = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, outputStream);
            // 加载电子签名图片
            Image signatureImage = Image.getInstance(signaturePath);
            signatureImage.setAbsolutePosition(400, 50); // 设置签名位置
            signatureImage.scaleToFit(200, 100); // 设置签名大小
            // 遍历每一页，添加电子签名
            int totalPages = reader.getNumberOfPages();
            for (int i = 1; i <= totalPages; i++) {
                PdfContentByte overContent = stamper.getOverContent(i);
                overContent.addImage(signatureImage);
            }
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
