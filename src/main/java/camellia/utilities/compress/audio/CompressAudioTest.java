package camellia.utilities.compress.audio;

import camellia.utilities.utils.compress.audio.VideoCompress;
import ws.schild.jave.EncoderException;

/**
 * @Datetime: 2024/11/5下午4:58
 * @author: Camellia.xioahua
 */
public class CompressAudioTest {
    public static void main(String[] args) throws EncoderException {
        String source = "E:\\视频压缩\\before\\1mintest.mp4";
        String target = "E:\\视频压缩\\after\\1mintest.mp4";
        VideoCompress.compressVideo(source,target);
    }
}
