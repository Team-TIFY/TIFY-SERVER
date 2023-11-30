package tify.server.domain.domains.question.repository;


import java.util.List;
import tify.server.domain.domains.question.dto.model.KnockedVo;
import tify.server.domain.domains.question.dto.model.MyKnockVo;

public interface KnockCustomRepository {

    List<MyKnockVo> searchMyKnockList(Long questionId, Long userId);

    List<KnockedVo> searchKnockToMeList(Long questionId, Long userId);
}
