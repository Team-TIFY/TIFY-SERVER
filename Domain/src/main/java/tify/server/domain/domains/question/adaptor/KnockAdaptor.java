package tify.server.domain.domains.question.adaptor;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.question.domain.Knock;
import tify.server.domain.domains.question.dto.model.KnockedVo;
import tify.server.domain.domains.question.dto.model.MyKnockVo;
import tify.server.domain.domains.question.exception.KnockNotFoundException;
import tify.server.domain.domains.question.repository.KnockRepository;

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

    public List<Knock> queryAllByDailyQuestionIdAndUserId(Long questionId, Long userId) {
        return knockRepository.findAllByDailyQuestionIdAndUserId(questionId, userId);
    }

    public List<Knock> queryAllByDailyQuestionIdAndUserIdAndKnockedUserId(
            Long questionId, Long userId, Long knockedUserId) {
        return knockRepository.findAllByDailyQuestionIdAndUserIdAndKnockedUserId(
                questionId, userId, knockedUserId);
    }

    public List<MyKnockVo> queryMyKnockList(Long questionId, Long userId) {
        return knockRepository.searchMyKnockList(questionId, userId);
    }

    public List<KnockedVo> queryKnockToMeList(Long questionId, Long userId) {
        return knockRepository.searchKnockToMeList(questionId, userId);
    }
}
