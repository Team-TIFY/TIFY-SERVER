package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.user.model.dto.request.PostUserOpinionRequest;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserOpinionAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserOpinion;

@UseCase
@RequiredArgsConstructor
public class CreateUserOpinionUseCase {

    private final UserUtils userUtils;
    private final UserOpinionAdaptor userOpinionAdaptor;

    @Transactional
    public void execute(PostUserOpinionRequest request) {
        User user = userUtils.getUser();
        UserOpinion userOpinion =
                new UserOpinion(
                        request.getOpinionType(),
                        user,
                        request.getTitle(),
                        request.getContent(),
                        request.getEmail(),
                        request.getFile());
        userOpinionAdaptor.save(userOpinion);
    }
}
