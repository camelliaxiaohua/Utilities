package utilities.compress.image;

import utils.compress.image.ImageCompressUtils;

import java.io.IOException;
import java.util.List;

/**
 * @Datetime: 2024/11/5下午5:07
 * @author: Camellia.xioahua
 */
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
