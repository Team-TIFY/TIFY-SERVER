package tify.server.domain.domains.user.repository;


import java.util.List;
import tify.server.domain.domains.user.vo.UserOnBoardingStatusInfoVo;

public interface UserOnBoardingCustomRepository {

    List<UserOnBoardingStatusInfoVo> searchByKeyword(String keyword);
}
