package tify.server.api.answer.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.answer.model.vo.KnockCountVo;
import tify.server.api.answer.model.vo.KnockInfoVo;
import tify.server.api.answer.model.vo.KnockToMeInfoVo;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.KnockAdaptor;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
public class RetrieveKnockUseCase {

    private final KnockAdaptor knockAdaptor;
    private final UserValidator userValidator;

    @Transactional(readOnly = true)
    public KnockCountVo executeCount(Long questionId, Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        userValidator.isValidUser(userId);
        userValidator.isResignedUser(userId);
        int size =
                knockAdaptor
                        .queryAllByDailyQuestionIdAndUserIdAndKnockedUserId(
                                questionId, currentUserId, userId)
                        .size();
        return KnockCountVo.of(currentUserId, userId, size);
    }

    @Transactional(readOnly = true)
    public List<KnockInfoVo> executeAll(Long questionId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return knockAdaptor.queryMyKnockList(questionId, currentUserId).stream()
                .map(KnockInfoVo::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<KnockToMeInfoVo> executeKnockToMe(Long questionId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return knockAdaptor.queryKnockToMeList(questionId, currentUserId).stream()
                .map(KnockToMeInfoVo::from)
                .toList();
    }
}
