package tify.server.domain.domains.tag.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.tag.domain.LargeCategory;

public interface LargeCategoryRepository extends JpaRepository<LargeCategory, Long> {}
