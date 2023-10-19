package tify.server.domain.domains.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.NeighborApplication;

public interface NeighborApplicationRepository
        extends JpaRepository<NeighborApplication, Long>, NeighborApplicationCustomRepository {}
