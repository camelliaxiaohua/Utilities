# 工具类

## 1. 视频压缩工具类

### 1.1 依赖

```xml
<!--视频压缩依赖，选择自己需要的版本。-->
<dependency>
    <groupId>ws.schild</groupId>
    <artifactId>jave-all-deps</artifactId>
    <version>3.5.0</version>
</dependency>
<dependency>
    <groupId>ws.schild</groupId>
    <artifactId>jave-core</artifactId>
    <version>3.5.0</version>
</dependency>
<dependency>
    <groupId>ws.schild</groupId>
    <artifactId>jave-nativebin-win64</artifactId>
    <version>3.5.0</version>
</dependency>
```

### 1.2 视频压缩率设置

```java
protected static int getFileSize(File file) {
    long fileSize = file.length(); // 获取文件大小 (单位: 字节)
    if (fileSize > 5 * 1024 * 1024 * 1024L) {
        return -1; // 超过 5GB，让用户重新上传。
    }
    if (fileSize > 2.5 * 1024 * 1024 * 1024L) { // 大于 2.5 GB 小于 5GB
        return 10;  // 设置较高的压缩比，可自行调整。
    } else if (fileSize > 1024 * 1024 * 1024L) { // 大于 1GB 小于 2.5GB
        return 9;   // 中等压缩比
    } else if (fileSize > 500 * 1024 * 1024L) {  // 大于 500MB 小于 1GB
        return 8;   // 较低压缩比
    } else {  // 小于 500MB
        return 6;
    }
}
```

### 1.3 使用方法

- 指明`传入路径`和`保存路径`。

```java
public class CompressAudioTest {
    public static void main(String[] args) throws EncoderException {
        String source = "E:\\视频压缩\\before\\1mintest.mp4";
        String target = "E:\\视频压缩\\after\\1mintest.mp4";
        VideoCompressUtils.compressVideo(source,target);
    }
}
```

---

## 2. 图片压缩工具类

### 2.1 依赖

- Java 标准库的一部分，不需要额外添加依赖。

### 2.2 图片压缩率设置

```java
protected static float calculateCompressionQuality(File file) {
    long fileSize = file.length(); // 获取文件大小 (单位: 字节)
    if (fileSize > 10 * 1024 * 1024) { //大于10不处理，重新上传。
        return -1.0f;
    }
    if (fileSize > 500 * 1024 * 1024) { // 大于 5MB
        return 0.1f;
    } else if (fileSize > 1 * 1024 * 1024) { // 大于 1MB 小于 5MB
        return 0.2f;
    } else {  // 小于 1MB
        return 0.5f;
    }
}
```

### 2.3 使用方法

```java
public class CompressImageTest {
    public static void main(String[] args) throws IOException {
        List<String> images = List.of("1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg");
        for (String image : images) {
            String source = "E:\\视频压缩\\before\\images\\"+image.toString();
            String target = "E:\\视频压缩\\after\\images\\"+ image.toString();
            ImageCompressUtils.compressImage(source , target);
        }
    }
}
```

---

## 3. 响应头工具类

### 3.1 依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 3.2 方法详细

- `exposHeaders()`暴露响应头

```java
return ResponseEntity.ok().headers(HttpHeaderUtils.exposHeaders()).body("生成验证码成功");
```

---

## 4. HTML转PDF

使用 Thymeleaf 模板引擎的方法生成 HTML 内容。生成的 HTML 随后会被处理并转换为字符串流（String）。接着，利用 Flying Saucer 库处理这个字符串流，将其转换为 PDF 文档格式。这一过程允许将动态数据填充到预定义的模板中，生成美观且格式化的 PDF 文件，适用于报告、发票等多种应用场景。

> **实现思路：生成HTML内容  ----> 处理成String ----> 使用Flying Saucer 库生成PDF。**

### 4.1 依赖 

```xml
<!--thymeleaf模板引擎依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactI
</dependency>
<!--Flying Saucer依赖（html->pdf）-->
<dependency>
    <groupId>org.eclipse.birt.runtime.3_7_1</groupId>
    <artifactId>com.lowagie.text</artifactId>
    <version>2.1.7</version>
</dependency>
<dependency>
    <groupId>org.xhtmlrenderer</groupId>
    <artifactId>flying-saucer-pdf</artifactId>
    <version>9.1.18</version>
</dependency>
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.4.2</version>
</dependency>
```

### 4.2 实现步骤

#### 4.2.1 实现参数接收类

```java
package camellia.utilities.thymeleaf.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Datetime: 2024/11/7上午9:41
 * @author: Camellia.xiaohua
 */
@Data
public class ParamEntity implements Serializable {

    private static final long serialVersionUID = 2971651722456178553L;

    // 基本类型
    private String name;
    private String value;

    // 数字类型
    private Integer intValue;
    private Long longValue;
    private BigDecimal decimalValue;

    // 布尔类型
    private Boolean isActive;

    // 日期时间类型
    private LocalDate dateValue;
    private LocalDateTime dateTimeValue;

    // 列表和数组类型
    private String[] tags;
    private List<String> interests;

    // 嵌套对象类型
    private ParamEntity relatedEntity;

    // 自定义格式化的值，例如金额格式
    public String getFormattedDecimalValue() {
        if (decimalValue != null) {
            return String.format("$%,.2f", decimalValue);
        }
        return null;
    }
    
}

```

#### 4.2.2 设计HTML模版

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Thymeleaf 渲染测试</title>
    <style>
        body {
            font-family: 'SimSun', '宋体', sans-serif;
        }
    </style>
</head>
<body>
<h1>Thymeleaf 渲染测试示例</h1>

<h2>基本信息</h2>
<p><strong>名称:</strong> <span th:text="${paramEntity.name}">默认名称</span></p>
<p><strong>值:</strong> <span th:text="${paramEntity.value}">默认值</span></p>

<h2>数值类型</h2>
<p><strong>整数值:</strong> <span th:text="${paramEntity.intValue}">0</span></p>
<p><strong>长整型值:</strong> <span th:text="${paramEntity.longValue}">0</span></p>
<p><strong>小数值:</strong> <span th:text="${paramEntity.formattedDecimalValue}">0.00</span></p>

<h2>布尔值</h2>
<p><strong>是否启用:</strong> <span th:text="${paramEntity.isActive ? '是' : '否'}">否</span></p>

<h2>日期与时间</h2>
<p><strong>日期:</strong> <span th:text="${paramEntity.dateValue}">2024-01-01</span></p>
<p><strong>日期时间:</strong> <span th:text="${paramEntity.dateTimeValue}">2024-01-01 12:00:00</span></p>

<h2>标签与兴趣</h2>
<p><strong>标签:</strong>
<ul>
    <li th:each="tag : ${paramEntity.tags}" th:text="${tag}">标签1</li>
</ul>
</p>
<p><strong>兴趣:</strong>
<ul>
    <li th:each="interest : ${paramEntity.interests}" th:text="${interest}">兴趣1</li>
</ul>
</p>

<h2>嵌套实体</h2>
<p><strong>相关实体名称:</strong> <span th:text="${paramEntity.relatedEntity.name}">默认名称</span></p>
<p><strong>相关实体值:</strong> <span th:text="${paramEntity.relatedEntity.value}">默认值</span></p>
<p><strong>相关实体是否启用:</strong> <span th:text="${paramEntity.relatedEntity.isActive ? '是' : '否'}">否</span></p>
</body>
</html>

```

直接传入一个对象到 Thymeleaf 模板中。要使这个对象在 Thymeleaf 模板中被渲染，你需要通过 `Context` 设置这个对象，然后在模板中使用表达式语言（如 `${paramEntity.name}`）来访问该对象的属性。

#### 4.2.3 数据传递到 Thymeleaf 模板引擎

`model.addAttribute("paramEntity", entity)` 将 `paramEntity` 对象传递给 Thymeleaf 模板，随后 `context.setVariables(model.asMap())` 会将模型中的数据传递给 `Context`，然后通过 `templateEngine.process("template", context)` 渲染模板。

```java
model.addAttribute("paramEntity", entity);
Context context = new Context();
context.setVariables(model.asMap());
```

> **注意：****使用程序手动控制模板渲染时非常有用，而不是使用 `ModelAndView` 自动处理视图。**
>
> **此外，需要对Thymeleaf 进行一些配置。**
>
> ```yml
> spring:
>   thymeleaf:
>     cache: false         # 关闭 Thymeleaf 的缓存，开发过程中无需重启
>     encoding: UTF-8      # 设置thymeleaf页面的编码
>     mode: HTML5          # 设置thymeleaf页面的模式
>     suffix: .html        # 设置thymeleaf页面的后缀
>     prefix: classpath:/templates/  # 设置thymeleaf页面的存储路径
> ```
>

#### 4.2.4 通过模版引擎渲染HTML

```java
String html = templateEngine.process("render", context);
```

> **渲染的结果刚好是String，第一个参数填写html模版的名称，第二个是我们传递的值。**

#### 4.2.5 生成PDF

**方法签名：**`public static ByteArrayOutputStream htmlConvertPdf(String html, String fontPath)`

- `String html`：渲染html
- `String fontPath`：自定义字体的路径。（中文需要自己提供字体包）

```java
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
```

### 4.3 使用案例及效果

```java
@PostMapping("/convert")
public ResponseEntity<byte[]> convert(@RequestBody ParamEntity entity, Model model) {
    model.addAttribute("paramEntity", entity);
    Context context = new Context();
    context.setVariables(model.asMap());
    String html = templateEngine.process("render", context);
    ByteArrayOutputStream pdfStream =  HtmlConvertPdf.htmlConvertPdf(html , "font/simsun.ttc");
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rendered.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body( pdfStream .toByteArray());
}
```

- 生成效果

![{55F5520C-0971-440C-9F07-5640BB9528E3}](https://camelliaxiaohua-1313958787.cos.ap-shanghai.myqcloud.com/postImage/202411081050252.png)

> 完整代
>
> 1. [控制层代码（测试）](https://github.com/camelliaxiaohua/Utilities/blob/utilities/src/main/java/camellia/utilities/thymeleaf/controller/ThymeleafController.java)
> 2. [工具类](https://github.com/camelliaxiaohua/Utilities/blob/utilities/src/main/java/camellia/utilities/utils/pdf/html/HtmlConvertPdf.java)



## 5. 电子章、电子签名。

首先需要准备电子公章和电子签名，然后将需要盖章签名的PDF加载成`ByteArrayOutputStream`流。调用工具类完成盖章和签名。

### 5.1 添加依赖

```xml
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.4.2</version>
</dependency>
```

### 5.2 编写工具类

#### 5.2.1 电子章（**指定盖章页码**）

```java
public static ByteArrayOutputStream addSeal(ByteArrayOutputStream pdf, Integer pageNum, String sealPat
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
```

- 设置电子章的位置和大小
  - **位置**：通过 `setAbsolutePosition(x, y)` 设置图片在页面上的位置（坐标原点是页面的左下角）。
  - **大小**：通过 `scaleToFit(width, height)` 设置图片的缩放大小。

#### 5.2.2 电子签名（**指定签名页码**）

```java
public static ByteArrayOutputStream addSignature(ByteArrayOutputStream pdf, Integer pageNum, String sig
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
```

- 设置电子签名的位置和大小
  - **位置**：通过 `setAbsolutePosition(x, y)` 设置图片在页面上的位置（坐标原点是页面的左下角）。
  - **大小**：通过 `scaleToFit(width, height)` 设置图片的缩放大小。


### 5.3 测试

```java
@PostMapping("/convert")
public ResponseEntity<byte[]> convert(@RequestBody ParamEntity entity, Model model) {
    model.addAttribute("paramEntity", entity);
    Context context = new Context();
    context.setVariables(model.asMap());
    String html = templateEngine.process("render", context);
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
```

![{FB47FDD1-7951-4E0B-86D0-CE221F8E3DEE}](https://camelliaxiaohua-1313958787.cos.ap-shanghai.myqcloud.com/postImage/202411092137229.png)

