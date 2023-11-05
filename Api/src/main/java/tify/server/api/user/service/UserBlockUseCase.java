package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserBlockAdaptor;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
public class UserBlockUseCase {

    private final UserBlockAdaptor userBlockAdaptor;
    private final UserValidator userValidator;

    @Transactional
    public void execute(Long toUserId) {
        Long userId = SecurityUtils.getCurrentUserId();
        userValidator.isNewBlock(userId, toUserId);
        userBlockAdaptor.save(userId, toUserId);
    }

    @Transactional
    public void delete(Long toUserId) {
        Long userId = SecurityUtils.getCurrentUserId();
        userValidator.isBlocked(userId, toUserId);
        userBlockAdaptor.delete(userId, toUserId);
    }
}
