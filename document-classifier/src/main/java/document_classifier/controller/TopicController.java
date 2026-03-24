package document_classifier.controller;

import document_classifier.dto.TopicRequest;
import document_classifier.entity.Topic;
import document_classifier.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public Topic addTopic(@RequestBody TopicRequest request) {

        Topic topic = Topic.builder()
                .title(request.getTitle())
                .keywords(request.getKeywords())
                .build();

        return topicService.addTopic(topic);
    }
}