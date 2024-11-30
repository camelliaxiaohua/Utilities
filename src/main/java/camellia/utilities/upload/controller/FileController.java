package camellia.utilities.upload.controller;

import camellia.utilities.upload.domain.UploadFile;
import camellia.utilities.upload.service.UploadFileService;
import camellia.utilities.upload.view.FileView;
import camellia.utilities.upload.view.Metadata;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Datetime: 2024/11/28上午10:09
 * @author: Camellia.xioahua
 */
@RestController
@RequestMapping("/upload")
@CrossOrigin
public class FileController {

    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/U01")
    public ResponseEntity<Stack<String>> uploadU01(@RequestParam("file1") MultipartFile file1,
                                                   @RequestParam("file2") MultipartFile file2) {
        if (file1.isEmpty() || file2.isEmpty()) {
            return ResponseEntity.badRequest().body(new Stack<>() {{
                push("文件为空，请上传有效文件");
            }});
        }
        String file1Name = file1.getOriginalFilename();
        String file2Name = file2.getOriginalFilename();
        double file1SizeMB = file1.getSize() / (1024 * 1024.0);
        double file2SizeMB = file2.getSize() / (1024 * 1024.0);
        Stack<String> fileInfos = new Stack<>();
        fileInfos.push(String.format("%s 的大小: %.2f MB", file1Name, file1SizeMB));
        fileInfos.push(String.format("%s 的大小: %.2f MB", file2Name, file2SizeMB));
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(fileInfos);
    }


    @PostMapping("/U02")
    public ResponseEntity<Stack<String>> uploadU02(@RequestPart MultipartFile file1,
                                                   @RequestPart MultipartFile file2) {
        if (file1.isEmpty() || file2.isEmpty()) {
            return ResponseEntity.badRequest().body(new Stack<>() {{
                push("文件为空，请上传有效文件");
            }});
        }
        String file1Name = file1.getOriginalFilename();
        String file2Name = file2.getOriginalFilename();
        double file1SizeMB = file1.getSize() / (1024 * 1024.0);
        double file2SizeMB = file2.getSize() / (1024 * 1024.0);
        Stack<String> fileInfos = new Stack<>();
        fileInfos.push(String.format("%s 的大小: %.2f MB", file1Name, file1SizeMB));
        fileInfos.push(String.format("%s 的大小: %.2f MB", file2Name, file2SizeMB));
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(fileInfos);
    }


    @PostMapping("/U03")
    public ResponseEntity<Stack<String>> uploadU03(@RequestParam Map<String, MultipartFile> videos) {
        if (videos == null || videos.isEmpty()) {
            Stack<String> errorStack = new Stack<>();
            errorStack.push("文件为空，请上传有效文件");
            return ResponseEntity.badRequest().body(errorStack);
        }
        Stack<String> fileInfos = new Stack<>();
        for (Map.Entry<String, MultipartFile> entry : videos.entrySet()) {
            String fileName = entry.getKey(); // 获取文件的字段名
            MultipartFile file = entry.getValue(); // 获取上传的文件
            double fileSizeMB = file.getSize()/(1024 * 1024.0);
            fileInfos.push(String.format("%s 的大小: %.2f MB", fileName, fileSizeMB));
        }
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(fileInfos);
    }


    @PostMapping("/U04")
    public ResponseEntity<Stack<String>> uploadU04(@RequestParam("file1") MultipartFile file1,
                                                   @RequestParam("file2") MultipartFile file2,
                                                   @RequestParam("text") String text) {
        if (file1 == null || file2 == null) {
            Stack<String> errorStack = new Stack<>();
            errorStack.push("文件为空，请上传有效文件");
            return ResponseEntity.badRequest().body(errorStack);
        }
        String file1Name = file1.getOriginalFilename();
        String file2Name = file2.getOriginalFilename();
        double file1SizeMB = file1.getSize() / (1024 * 1024.0);
        double file2SizeMB = file2.getSize() / (1024 * 1024.0);
        Stack<String> fileInfos = new Stack<>();
        fileInfos.push(String.format("%s 的大小: %.2f MB", file1Name, file1SizeMB));
        fileInfos.push(String.format("%s 的大小: %.2f MB", file2Name, file2SizeMB));
        fileInfos.push(String.format("上传的文本内容: %s", text));
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(fileInfos);
    }

    @PostMapping("/U05")
    public ResponseEntity<Stack<String>> uploadU05(@RequestPart("file1") MultipartFile file1,
                                                   @RequestPart("file2") MultipartFile file2,
                                                   @RequestPart("metadata") Metadata metadata) {
        if (file1.isEmpty() || file2.isEmpty()) {
            Stack<String> errorStack = new Stack<>();
            errorStack.push("文件为空，请上传有效文件");
            return ResponseEntity.badRequest().body(errorStack);
        }
        String file1Name = file1.getOriginalFilename();
        String file2Name = file2.getOriginalFilename();
        double file1SizeMB = file1.getSize() / (1024 * 1024.0);
        double file2SizeMB = file2.getSize() / (1024 * 1024.0);
        Stack<String> fileInfos = new Stack<>();
        fileInfos.push(String.format("%s 的大小: %.2f MB", file1Name, file1SizeMB));
        fileInfos.push(String.format("%s 的大小: %.2f MB", file2Name, file2SizeMB));
        fileInfos.push(String.format("上传的文本内容: %s", JSON.toJSONString(metadata)));
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(fileInfos);
    }


    @PostMapping("/U06")
    public ResponseEntity<Stack<String>> uploadU06(@ModelAttribute FileView fileView){
        MultipartFile file1 = fileView.getFile1();
        MultipartFile file2 = fileView.getFile2();
        if (file1 == null || file2 == null) {
            Stack<String> errorStack = new Stack<>();
            errorStack.push("文件为空，请上传有效文件");
            return ResponseEntity.badRequest().body(errorStack);
        }
        String file1Name = file1.getOriginalFilename();
        String file2Name = file2.getOriginalFilename();
        double file1SizeMB = file1.getSize() / (1024 * 1024.0);
        double file2SizeMB = file2.getSize() / (1024 * 1024.0);
        Stack<String> fileInfos = new Stack<>();
        fileInfos.push(String.format("%s 的大小: %.2f MB", file1Name, file1SizeMB));
        fileInfos.push(String.format("%s 的大小: %.2f MB", file2Name, file2SizeMB));
        fileInfos.push(String.format("上传的文本内容: %s", fileView.getText()));
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(fileInfos);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable("id") Long id) {
        // 根据 id 查找文件
        UploadFile uploadFile = uploadFileService.getById(id);
        // 如果没有找到文件，抛出异常
        if (uploadFile == null) {
            throw new RuntimeException("文件不存在");
        }
        byte[] content = uploadFile.getContent();
        return ResponseEntity.ok().header("Content-Type", "application/pdf").body(content);
    }

}
