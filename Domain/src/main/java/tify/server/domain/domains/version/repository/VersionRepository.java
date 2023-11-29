package tify.server.domain.domains.version.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.version.domain.Version;

public interface VersionRepository extends JpaRepository<Version, Long> {}
