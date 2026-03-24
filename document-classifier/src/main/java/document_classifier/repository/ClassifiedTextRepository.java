package document_classifier.repository;

import document_classifier.entity.ClassifiedText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassifiedTextRepository extends JpaRepository<ClassifiedText, Long> {

    List<ClassifiedText> findByDocumentId(Long documentId);
}