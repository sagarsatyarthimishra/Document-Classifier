package document_classifier.repository;

import document_classifier.entity.ClassifiedText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface ClassifiedTextRepository extends JpaRepository<ClassifiedText, Long> {

    Page<ClassifiedText> findByDocumentId(Long documentId, Pageable pageable);
}