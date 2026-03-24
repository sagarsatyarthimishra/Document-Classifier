package document_classifier.controller;

import document_classifier.dto.ClassificationResponse;
import document_classifier.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Document APIs", description = "APIs for uploading and classifying documents")
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @Operation(
            summary = "Upload document (TEXT or PDF)",
            description = "Upload either raw text or a PDF file. System will extract text, split into sentences and classify into topics."
    )
    // Upload document (text OR file)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadDocument(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) MultipartFile file
    ) throws Exception {

        Long docId = documentService.processDocument(text, file);

        return "Document processed successfully. ID: " + docId;
    }

    @Operation(
            summary = "Get classification results",
            description = "Retrieve classified text chunks along with assigned topics and confidence scores"
    )
    //  Get results
//    @GetMapping("/{id}/results")
//    public Map<String, Object> getResults(@PathVariable Long id) {
//
//        List<ClassificationResponse> results = documentService.getResults(id);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("documentId", id);
//        response.put("results", results);
//
//        if (results.isEmpty()) {
//            response.put("message", "No classification results found");
//        }
//
//        return response;
//    }
    @GetMapping("/{id}/results")
    public Map<String, Object> getResults(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        // 🔥 Direct service ka response return karo
        Map<String, Object> response = documentService.getResults(id, page, size);

        return response;
    }
}