package document_classifier.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassificationResponse {

    private String text;
    private String assignedTopic;
    private Double confidence;
}