package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.user.model.dto.request.PatchExpoTokenRequest;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;

@UseCase
@RequiredArgsConstructor
public class UpdateUserExpoTokenUseCase {

    private final UserAdaptor userAdaptor;

    @Transactional
    public void execute(Long userId, PatchExpoTokenRequest request) {
        User user = userAdaptor.query(userId);
        user.updateUserExpoToken(request.getExpoToken());
    }
}
