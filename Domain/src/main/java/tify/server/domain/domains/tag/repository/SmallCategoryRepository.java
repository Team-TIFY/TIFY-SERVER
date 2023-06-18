package tify.server.domain.domains.tag.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.tag.domain.SmallCategory;

public interface SmallCategoryRepository extends JpaRepository<SmallCategory, Long> {}
