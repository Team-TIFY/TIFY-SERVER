package tify.server.domain.domains.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.common.vo.UserFavorVo;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.domain.UserFavor;

import java.util.List;

public interface UserFavorRepository extends JpaRepository<UserFavor, Long>, UserFavorCustomRepository {
}
