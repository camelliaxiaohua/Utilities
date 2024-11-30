package camellia.utilities.utils.compress.audio;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;

/**
 * 视频压缩工具类
 */
public class VideoCompress {

    public static void compressVideo(String sourcePath , String targetPath) throws EncoderException {
        // 压缩前文件路径
        File source = new File(sourcePath);
        // 压缩后的文件路径
        File target = new File(targetPath);
        // 获取文件大小
        int rate = getFileSize(source);
        if (rate != 0) {
            Long totalBits = getTotalBits(source);
            startCompressVideo(source, target, rate ,totalBits);
        } else {
            System.out.println("视频文件超过可压缩大小！");
        }
    }

    /**
     * 根据文件大小计，判断文件是否压缩。超过限度就不给压缩，重新上传。
     * @param file 源文件
     * @return 压缩比
     */
    protected static int getFileSize(File file) {
        long fileSize = file.length(); // 获取文件大小 (单位: 字节)
        if (fileSize > 5 * 1024 * 1024 * 1024L) {
            return -1; // 超过 5GB，不进行压缩。
        }
        if (fileSize > 2.5 * 1024 * 1024 * 1024L) { // 大于 2.5 GB 小于 5GB
            return 10;  // 设置较高的压缩比
        } else if (fileSize > 1024 * 1024 * 1024L) { // 大于 1GB 小于 2.5GB
            return 9;   // 中等压缩比
        } else if (fileSize > 500 * 1024 * 1024L) {  // 大于 500MB 小于 1GB
            return 8;   // 较低压缩比
        } else {  // 小于 500MB
            return 6;
        }
    }

    /**
     * 视频压缩
     * @param source 源文件
     * @param target 目标文件
     */
    protected static void startCompressVideo(File source, File target, int rate ,Long totalBits){
        try {
            System.out.println("---------------开始压缩---------------");
            long start = System.currentTimeMillis();
            // 音频编码属性配置
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(64000); // 设置音频比特率（128 kbps）
            audio.setChannels(1);
            audio.setSamplingRate(22050);

            // 视频编码属性配置
            VideoAttributes video = new VideoAttributes();
            video.setCodec("libx264");  // 使用更高效的 H.264 编码
            video.setFrameRate(15);  // 设置较高的帧率
            video.setBitRate((int) (totalBits/rate));
            // 编码设置
            EncodingAttributes attr = new EncodingAttributes();
            attr.setOutputFormat("mp4");
            attr.setAudioAttributes(audio);
            attr.setVideoAttributes(video);

            // 执行压缩
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attr);

            System.out.println("---------------结束压缩---------------");
            long end = System.currentTimeMillis();
            System.out.println("压缩前大小：" + source.length() + " 字节, 压缩后大小：" + target.length() + " 字节");
            System.out.println("压缩耗时：" + (end - start) /1000 + "秒");
        } catch (EncoderException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取视频的总比特率
     * @param source 视频文件
     * @return 视频的总比特率（单位：bit/s）
     */
    protected static Long getTotalBits(File source) throws EncoderException {
        // 获取视频的文件大小（字节）
        long fileSize = source.length();
        // 使用 ws.schild.jave 获取视频时长（秒）
        MultimediaObject multimediaObject = new MultimediaObject(source);
        MultimediaInfo mediaInfo = multimediaObject.getInfo();
        double duration = mediaInfo.getDuration() / 1000.0;  // 时长，单位秒
        // 计算比特率 (单位：bit/s)
        double bitRate = (fileSize * 8) / duration;
        return (long) bitRate;
    }

}