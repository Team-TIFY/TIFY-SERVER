package tify.server.api.answer.model.response;


import lombok.Builder;
import lombok.Getter;
import tify.server.api.answer.model.vo.AnswerInfoVo;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

@Getter
@Builder
public class NeighborAnswerInfoDTO {

    private final RetrieveNeighborDTO neighborInfo;

    private final AnswerInfoVo answerInfo;
}
