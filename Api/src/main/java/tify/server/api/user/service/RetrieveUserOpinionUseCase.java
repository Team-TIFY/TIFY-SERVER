package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.api.user.model.dto.vo.UserOpinionVo;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserOpinionAdaptor;

@UseCase
@RequiredArgsConstructor
public class RetrieveUserOpinionUseCase {

    private final UserUtils userUtils;
    private final UserOpinionAdaptor userOpinionAdaptor;

    public UserOpinionVo execute(Long id) {
        return UserOpinionVo.from(userOpinionAdaptor.query(id));
    }

    public List<UserOpinionVo> executeAll() {
        return userOpinionAdaptor.queryAllByUser(userUtils.getUser()).stream()
                .map(UserOpinionVo::from)
                .toList();
    }
}
