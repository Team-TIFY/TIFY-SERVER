package tify.server.domain.domains.user.adaptor;

import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.Knock;
import tify.server.domain.domains.user.exception.KnockNotFoundException;
import tify.server.domain.domains.user.repository.KnockRepository;

@Adaptor
@RequiredArgsConstructor
public class KnockAdaptor {

    private final KnockRepository knockRepository;

    public Knock query(Long knockId) {
        return knockRepository
                .findById(knockId)
                .orElseThrow(() -> KnockNotFoundException.EXCEPTION);
    }

    public Knock save(Knock knock) {
        return knockRepository.save(knock);
    }
}
