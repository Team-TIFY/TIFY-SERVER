package tify.server.api.answer.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.answer.model.response.NeighborAnswerInfoDTO;
import tify.server.api.answer.model.vo.AnswerInfoVo;
import tify.server.api.answer.model.vo.RetrieveAnswerVo;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.adaptor.DailyQuestionAdaptor;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.domain.DailyQuestion;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserBlockAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserBlock;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class RetrieveDailyAnswerUseCase {

    private final UserAdaptor userAdaptor;
    private final AnswerAdaptor answerAdaptor;
    private final UserBlockAdaptor userBlockAdaptor;
    private final NeighborAdaptor neighborAdaptor;
    private final DailyQuestionAdaptor dailyQuestionAdaptor;

    @Transactional(readOnly = true)
    public List<RetrieveAnswerVo> execute(Long questionId) {
        DailyQuestion dailyQuestion = dailyQuestionAdaptor.query(questionId);
        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<Long> userIdList =
                new ArrayList<>(
                        neighborAdaptor.queryAllByFromUserIdAndIsView(currentUserId, true).stream()
                                .map(Neighbor::getToUserId)
                                .toList());
        userIdList.add(currentUserId);
        AnswerCondition answerCondition = new AnswerCondition(dailyQuestion.getId(), userIdList);
        return answerAdaptor.searchAnswer(currentUserId, answerCondition).stream()
                .map(answerVo -> RetrieveAnswerVo.of(answerVo, currentUserId))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NeighborAnswerInfoDTO> executeNeighborAnswerList(
            Long questionId, Long userId) {
        List<User> neighborList =
                neighborAdaptor.queryAllByFromUserId(userId).stream()
                        .map(Neighbor::getToUserId)
                        .map(userAdaptor::query)
                        .toList();
        List<Long> blockedIdList =
                userBlockAdaptor.queryAllByFromUserId(userId).stream()
                        .map(UserBlock::getToUserId)
                        .toList();
        List<Long> friendIdList =
                neighborAdaptor.queryAllByFromUserId(userId).stream()
                        .map(Neighbor::getToUserId)
                        .toList();
        NeighborCondition neighborCondition =
                new NeighborCondition(userId, blockedIdList, friendIdList);
        List<RetrieveNeighborDTO> neighbors = neighborAdaptor.searchNeighbors(neighborCondition);
        return neighbors.stream()
                .map(
                        dto -> {
                            Optional<Answer> answer =
                                    answerAdaptor.optionalQueryByQuestionAndUser(
                                            questionId, dto.getNeighborId());
                            return NeighborAnswerInfoDTO.builder()
                                    .neighborInfo(dto)
                                    .answerInfo(AnswerInfoVo.from(answer.orElse(null)))
                                    .build();
                        })
                .toList();
    }
}
