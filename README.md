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



  





