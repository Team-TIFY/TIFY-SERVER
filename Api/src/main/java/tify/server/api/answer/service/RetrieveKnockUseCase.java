package tify.server.api.answer.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.api.answer.model.vo.KnockCountVo;
import tify.server.api.answer.model.vo.KnockInfoVo;
import tify.server.api.answer.model.vo.KnockToMeInfoVo;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.KnockAdaptor;

@UseCase
@RequiredArgsConstructor
public class RetrieveKnockUseCase {

    private final KnockAdaptor knockAdaptor;

    public KnockCountVo executeCount(Long questionId, Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        int size =
                knockAdaptor
                        .queryAllByDailyQuestionIdAndUserIdAndKnockedUserId(
                                questionId, currentUserId, userId)
                        .size();
        return KnockCountVo.builder()
                .fromUserId(currentUserId)
                .knockedUserId(userId)
                .knockCount(size)
                .build();
    }

    public List<KnockInfoVo> executeAll(Long questionId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return knockAdaptor.queryMyKnockList(questionId, currentUserId).stream()
                .map(KnockInfoVo::from)
                .toList();
    }

    public List<KnockToMeInfoVo> executeKnockToMe(Long questionId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return knockAdaptor.queryKnockToMeList(questionId, currentUserId).stream()
                .map(KnockToMeInfoVo::from)
                .toList();
    }
}
