package tify.server.domain.domains.user.repository;


import java.util.List;

import org.springframework.stereotype.Repository;
import tify.server.domain.common.vo.UserFavorVo;
import tify.server.domain.domains.user.domain.LargeCategory;

public interface UserFavorCustomRepository {
    List<UserFavorVo> findByLargeCategory(LargeCategory largeCategory);
}
