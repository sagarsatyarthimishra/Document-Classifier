package document_classifier.controller;

import document_classifier.dto.ClassificationResponse;
import document_classifier.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    // 🔥 Upload document (text OR file)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadDocument(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) MultipartFile file
    ) throws Exception {

        Long docId = documentService.processDocument(text, file);

        return "Document processed successfully. ID: " + docId;
    }

    // 🔹 Get results
    @GetMapping("/{id}/results")
    public Map<String, Object> getResults(@PathVariable Long id) {

        List<ClassificationResponse> results = documentService.getResults(id);

        Map<String, Object> response = new HashMap<>();
        response.put("documentId", id);
        response.put("results", results);

        if (results.isEmpty()) {
            response.put("message", "No classification results found");
        }

        return response;
    }
}