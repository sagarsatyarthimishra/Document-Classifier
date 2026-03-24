package document_classifier.service;

import document_classifier.entity.Topic;
import document_classifier.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClassificationService {

    private final TopicRepository topicRepository;

    // 🔹 Extract text from PDF
    public String extractTextFromPdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    // 🔹 Split into sentences
    public List<String> splitText(String text) {

        return Arrays.stream(text.split("[\\.\\n]"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    // 🔹 Classification logic
    public Map<String, Object> classify(String sentence) {

        List<Topic> topics = topicRepository.findAll();

        Topic bestTopic = null;
        int maxScore = 0;

        for (Topic topic : topics) {
            int score = 0;

            for (String keyword : topic.getKeywords()) {
                if (sentence.toLowerCase().contains(keyword.toLowerCase())) {
                    score++;
                }
            }

            if (score > maxScore) {
                maxScore = score;
                bestTopic = topic;
            }
        }

        Map<String, Object> result = new HashMap<>();

        // 🔥 YAHI IMPORTANT PART HAI
        if (bestTopic == null || maxScore == 0) {
            result.put("topic", "UNCLASSIFIED");
            result.put("confidence", 0.0);
        } else {
            double confidence = (double) maxScore / bestTopic.getKeywords().size();
            result.put("topic", bestTopic.getTitle());
            result.put("confidence", confidence);
        }

        return result;
    }
}