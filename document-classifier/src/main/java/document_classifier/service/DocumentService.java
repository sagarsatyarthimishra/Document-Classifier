package document_classifier.service;

import document_classifier.dto.ClassificationResponse;
import document_classifier.entity.ClassifiedText;
import document_classifier.entity.Document;
import document_classifier.repository.ClassifiedTextRepository;
import document_classifier.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ClassifiedTextRepository classifiedTextRepository;
    private final ClassificationService classificationService;

    public Long processDocument(String text, MultipartFile file) throws Exception {

        // 🔥 Validation: only one input allowed
        if ((text == null || text.isEmpty()) && (file == null || file.isEmpty())) {
            throw new RuntimeException("Either text or file must be provided");
        }

        if (text != null && !text.isEmpty() && file != null && !file.isEmpty()) {
            throw new RuntimeException("Provide either text or file, not both");
        }

        String extractedText;

        // 🔹 Case 1: TEXT input
        if (text != null && !text.isEmpty()) {
            extractedText = text;
        }
        // 🔹 Case 2: PDF input
        else {
            extractedText = classificationService.extractTextFromPdf(file);
        }

        // 🔹 Save document
        Document document = Document.builder()
                .originalText(extractedText)
                .createdAt(LocalDateTime.now())
                .build();

        Document savedDoc = documentRepository.save(document);

        // 🔹 Split text
        List<String> sentences = classificationService.splitText(extractedText);

        // 🔹 Classify each sentence
        for (String sentence : sentences) {

            if (sentence.trim().isEmpty()) continue;

            Map<String, Object> result = classificationService.classify(sentence);

            ClassifiedText classified = ClassifiedText.builder()
                    .documentId(savedDoc.getId())
                    .textChunk(sentence.trim())
                    .topicName((String) result.get("topic"))
                    .confidence((Double) result.get("confidence"))
                    .build();

            classifiedTextRepository.save(classified);
        }

        return savedDoc.getId();
    }

    // 🔹 Get results
    public List<ClassificationResponse> getResults(Long documentId) {

        // 🔥 check document exists
        if (!documentRepository.existsById(documentId)) {
            throw new RuntimeException("Document not found");
        }

        List<ClassifiedText> list = classifiedTextRepository.findByDocumentId(documentId);

        List<ClassificationResponse> response = new ArrayList<>();

        for (ClassifiedText c : list) {
            response.add(
                    ClassificationResponse.builder()
                            .text(c.getTextChunk())
                            .assignedTopic(c.getTopicName())
                            .confidence(c.getConfidence())
                            .build()
            );
        }

        return response;
    }
}