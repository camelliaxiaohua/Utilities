package camellia.utilities.utils.compress.image;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompress {

    public static void compressImage(String sourcePath , String targetPath) throws IOException {
        // 压缩前文件路径
        File source = new File(sourcePath);
        // 压缩后的文件路径
        File target = new File(targetPath);
        // 根据文件大小动态计算压缩比
        float compressionQuality = calculateCompressionQuality(source);
        if (compressionQuality > 0 ) startCompressImage(source, target, compressionQuality);
        else System.out.println("图片太大，重新上传！");
    }

    /**
     * 根据文件大小计算压缩质量
     * @param file 图片文件
     * @return 压缩质量 0.0f - 1.0f
     */
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

    /**
     * 压缩图片
     * @param source 原始图片文件
     * @param target 目标压缩图片文件
     * @param quality 压缩质量 0.0f - 1.0f
     * @throws IOException
     */
    protected static void startCompressImage(File source, File target, float quality) throws IOException {
        // 读取源图像
        System.out.println("文件路径: " + source.getAbsolutePath());
        BufferedImage image = ImageIO.read(source);
        // 设置输出的图像格式和压缩质量
        FileOutputStream out = new FileOutputStream(target);
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        // 设置压缩质量
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        // 写入压缩后的图像
        writer.setOutput(ImageIO.createImageOutputStream(out));
        writer.write(null, new javax.imageio.IIOImage(image, null, null), param);
        out.close();
        writer.dispose();
        System.out.println("图片压缩完成，输出路径：" + target.getAbsolutePath());
    }

}
