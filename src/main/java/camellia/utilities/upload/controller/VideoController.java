package camellia.utilities.upload.controller;

import camellia.utilities.common.ApiResponse;
import camellia.utilities.common.ApiUtils;
import camellia.utilities.utils.compress.audio.VideoCompress;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Datetime: 2024/12/2下午10:33
 * @author: Camellia.xioahua
 */
@CrossOrigin
@RestController
@RequestMapping("/video")
public class VideoController {

    @PostMapping("/upload")
    public ApiResponse<String> uploadVideo(@RequestPart("video")MultipartFile video) throws IOException {
        String uploadDir = "F:/upload/video"; // 服务器上保存视频的路径
        File directory = new File(uploadDir);
        if (!directory.exists()) directory.mkdirs(); // 如果目录不存在，创建目录
        String fileName = RandomStringUtils.randomAlphanumeric(10) + "-" + video.getOriginalFilename();
        File destination = new File(uploadDir, fileName);
        // 保存原始视频文件到服务器
        video.transferTo(destination);
        // 压缩视频路径
        String compressedFileName = "compressed-" + fileName; // 压缩后文件的名称
        File compressedDestination = new File(uploadDir, compressedFileName);
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.submit(() -> {
            // 调用压缩工具类压缩视频
            try {
                VideoCompress.compressVideo(destination.getAbsolutePath(), compressedDestination.getAbsolutePath());
                // 删除源视频文件（压缩完成后）
                if (destination.exists()) {
                    boolean deleted = destination.delete(); // 删除源视频文件
                    if (deleted) {
                        System.out.println("源视频文件已删除: " + destination.getAbsolutePath());
                    } else {
                        System.out.println("删除源视频文件失败: " + destination.getAbsolutePath());
                    }
                }
            } catch (EncoderException e) {
                e.printStackTrace();
            }
        });
        es.shutdown();
        // 生成保存到数据库的压缩视频路径（相对路径）
        String videoPath = "F:/upload/video/" + compressedFileName;
        return ApiUtils.success(videoPath);
    }

}
