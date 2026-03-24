package document_classifier.dto;

import lombok.Data;
import java.util.List;

@Data
public class TopicRequest {
    private String title;
    private List<String> keywords;
}