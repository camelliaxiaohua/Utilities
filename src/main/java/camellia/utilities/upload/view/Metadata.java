package camellia.utilities.upload.view;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Metadata implements Serializable {

    private static final long serialVersionUID = 3142702190993895316L;

    private String content;

    private String id;

    private List<String> tags;
}
//{
//        "content": "This is some metadata content.",
//        "id": "12345",
//        "tags": ["tag1", "tag2", "tag3"]
//}
