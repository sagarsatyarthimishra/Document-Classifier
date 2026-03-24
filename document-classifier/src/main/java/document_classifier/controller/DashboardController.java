package document_classifier.controller;

import document_classifier.entity.ClassifiedText;
import document_classifier.repository.ClassifiedTextRepository;
import document_classifier.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DocumentRepository documentRepository;
    private final ClassifiedTextRepository classifiedTextRepository;

    @GetMapping
    public Map<String, Object> getDashboard() {

        Map<String, Object> response = new HashMap<>();

        long totalDocuments = documentRepository.count();
        long totalChunks = classifiedTextRepository.count();

        response.put("totalDocuments", totalDocuments);
        response.put("totalChunks", totalChunks);

        // topic distribution
        List<String> data = classifiedTextRepository.findAll()
                .stream()
                .map(ClassifiedText::getTopicName)
                .toList();

        Map<String, Long> distribution = new HashMap<>();

        for (String topic : data) {
            distribution.put(topic, distribution.getOrDefault(topic, 0L) + 1);
        }

        response.put("topicDistribution", distribution);

        return response;
    }
}