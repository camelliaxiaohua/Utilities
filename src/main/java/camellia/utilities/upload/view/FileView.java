package camellia.utilities.upload.view;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @Datetime: 2024/11/28上午10:08
 * @author: Camellia.xioahua
 */
@Data
public class FileView implements Serializable {

    private static final long serialVersionUID = -1907364471419127058L;
    /**
     * 文件1
     */
    private MultipartFile file1;
    /**
     * 文件2
     */
    private MultipartFile file2;
    /**
     * id
     */
    private String text;
}
