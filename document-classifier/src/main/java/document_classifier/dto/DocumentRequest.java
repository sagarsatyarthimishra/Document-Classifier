package document_classifier.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentRequest {

    private String text;

    private MultipartFile file;
}