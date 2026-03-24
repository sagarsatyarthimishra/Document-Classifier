package document_classifier.controller;

import document_classifier.dto.TopicRequest;
import document_classifier.entity.Topic;
import document_classifier.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Topic APIs", description = "APIs for managing classification topics")
@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @Operation(
            summary = "Add new topic",
            description = "Create a topic with title and keywords used for classification"
    )

    @PostMapping
    public Topic addTopic(@RequestBody TopicRequest request) {

        Topic topic = Topic.builder()
                .title(request.getTitle())
                .keywords(request.getKeywords())
                .build();

        return topicService.addTopic(topic);
    }
}