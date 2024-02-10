package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;

@UseCase
@RequiredArgsConstructor
public class UpdateAlarmReceiveUseCase {

    private final UserAdaptor userAdaptor;

    @Transactional
    public void execute() {
        User currentUser = userAdaptor.query(SecurityUtils.getCurrentUserId());
        currentUser.updateUserReceiveAlarm();
    }
}
