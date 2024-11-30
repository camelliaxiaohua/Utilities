package camellia.utilities.thymeleaf.controller;

import camellia.utilities.thymeleaf.model.ParamEntity;
import camellia.utilities.utils.pdf.electronic.PdfElectronic;
import camellia.utilities.utils.pdf.html.HtmlConvertPdf;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;


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
    public ResponseEntity<byte[]> convert(@RequestBody ParamEntity entity, Model model) {
        model.addAttribute("paramEntity", entity);
        Context context = new Context();
        context.setVariables(model.asMap());
        String html = templateEngine.process("table1", context);
        ByteArrayOutputStream pdfStream =  HtmlConvertPdf.htmlConvertPdf(html , "font/simsun.ttc");
        // 添加公章和电子签名图片
        String sealPath = "E:/workspace/camellia/Utilities/src/main/resources/imags/camellia.png";
        String signaturePath = "E:/workspace/camellia/Utilities/src/main/resources/imags/signature.png";
        pdfStream = PdfElectronic.addSeal( pdfStream , 1, sealPath);
        pdfStream  = PdfElectronic.addSignature( pdfStream , 1, signaturePath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rendered.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body( pdfStream .toByteArray());
    }

    @GetMapping("/preview")
    public ResponseEntity<byte[]> preview() {
        Context context = new Context();
        context.setVariables(new HashMap<>());
        String html = templateEngine.process("table1", context);
        ByteArrayOutputStream pdfStream =  HtmlConvertPdf.htmlConvertPdf(html , "font/simsun.ttc");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rendered.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body( pdfStream .toByteArray());
    }
}
