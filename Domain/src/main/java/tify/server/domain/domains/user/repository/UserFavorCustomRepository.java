package tify.server.domain.domains.user.repository;


import java.util.List;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.vo.UserFavorVo;

public interface UserFavorCustomRepository {
    List<UserFavorVo> findByLargeCategory(LargeCategory largeCategory);
}
