package camellia.utilities.thymeleaf.controller;

import camellia.utilities.thymeleaf.model.ParamEntity;
import camellia.utilities.utils.pdf.electronic.PdfElectronic;
import camellia.utilities.utils.pdf.html.HtmlConvertPdf;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;


@Controller
@CrossOrigin
public class ThymeleafController {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @PostMapping("/render")
    public String renderHTML(@RequestBody ParamEntity entity, Model model) {
        model.addAttribute("paramEntity", entity);
        return "render";
    }


    @PostMapping("/convert")
    public ResponseEntity<byte[]> convert(@RequestBody ParamEntity entity, Model model) throws DocumentException, IOException, com.itextpdf.text.DocumentException {
        model.addAttribute("paramEntity", entity);
        Context context = new Context();
        context.setVariables(model.asMap());
        String html = templateEngine.process("render", context);
        ByteArrayOutputStream pdfOutputStream =  HtmlConvertPdf.htmlConvertPdf(html);

        // 添加公章和电子签名图片
        pdfOutputStream = PdfElectronic.addSeal(pdfOutputStream);
        pdfOutputStream = PdfElectronic.addSignature(pdfOutputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rendered.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfOutputStream.toByteArray());
    }

    private ByteArrayOutputStream addSealsAndSignatures(ByteArrayOutputStream pdfOutputStream) throws DocumentException, IOException, com.itextpdf.text.DocumentException {
        PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfOutputStream.toByteArray()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, outputStream);

        // 添加公章
        Image sealImage = Image.getInstance("E:/workspace/camellia/Utilities/src/main/resources/imags/seal.png");
        sealImage.setAbsolutePosition(50, 50); // 设置公章的位置
        sealImage.scaleToFit(150, 150); // 设置公章的大小
        PdfContentByte overContent = stamper.getOverContent(1); // 获取第一页的内容层
        overContent.addImage(sealImage);

        // 添加电子签名图片
        Image signatureImage = Image.getInstance("E:/workspace/camellia/Utilities/src/main/resources/imags/signature.png");
        signatureImage.setAbsolutePosition(400, 50); // 设置电子签名的位置
        signatureImage.scaleToFit(200, 100); // 设置电子签名的大小
        overContent.addImage(signatureImage);

        stamper.close();
        reader.close();

        return outputStream;
    }

}
