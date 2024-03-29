package tify.server.api.answer.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.answer.model.response.NeighborAnswerInfoDTO;
import tify.server.api.answer.model.vo.AnswerInfoVo;
import tify.server.api.answer.model.vo.RetrieveAnswerVo;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.adaptor.DailyQuestionAdaptor;
import tify.server.domain.domains.question.adaptor.KnockAdaptor;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.domain.DailyQuestion;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserResignAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.UserResign;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class RetrieveDailyAnswerUseCase {

    private final AnswerAdaptor answerAdaptor;
    private final NeighborAdaptor neighborAdaptor;
    private final DailyQuestionAdaptor dailyQuestionAdaptor;
    private final KnockAdaptor knockAdaptor;
    private final UserResignAdaptor userResignAdaptor;

    @Transactional(readOnly = true)
    public List<RetrieveAnswerVo> execute(Long questionId) {
        DailyQuestion dailyQuestion = dailyQuestionAdaptor.query(questionId);
        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<Long> resignedUserId =
                userResignAdaptor.queryAll().stream().map(UserResign::getUserId).toList();
        List<Long> userIdList =
                new ArrayList<>(
                        neighborAdaptor.queryAllByFromUserIdAndIsView(currentUserId, true).stream()
                                .map(Neighbor::getToUserId)
                                .filter(id -> !resignedUserId.contains(id))
                                .toList());
        userIdList.add(currentUserId);
        AnswerCondition answerCondition = new AnswerCondition(dailyQuestion.getId(), userIdList);
        return answerAdaptor.searchAnswer(currentUserId, answerCondition).stream()
                .map(answerVo -> RetrieveAnswerVo.of(answerVo, currentUserId))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NeighborAnswerInfoDTO> executeNeighborAnswerList(Long questionId, Long userId) {
        List<RetrieveNeighborDTO> neighbors = neighborAdaptor.searchNeighbors(userId);
        return neighbors.stream()
                .map(
                        dto -> {
                            Optional<Answer> answer =
                                    answerAdaptor.optionalQueryByQuestionAndUser(
                                            questionId, dto.getNeighborUserId());
                            return NeighborAnswerInfoDTO.builder()
                                    .neighborInfo(dto)
                                    .answerInfo(AnswerInfoVo.from(answer.orElse(null)))
                                    .isNeighborKnocked(
                                            !knockAdaptor
                                                    .queryAllByDailyQuestionIdAndUserIdAndKnockedUserId(
                                                            questionId, userId, dto.getNeighborId())
                                                    .isEmpty())
                                    .build();
                        })
                .toList();
    }
}
