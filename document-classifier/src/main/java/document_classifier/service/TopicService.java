package document_classifier.service;

import document_classifier.entity.Topic;
import document_classifier.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }
}