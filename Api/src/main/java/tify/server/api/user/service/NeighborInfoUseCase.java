package tify.server.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.common.vo.UserProfileVo;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NeighborInfoUseCase {
  
  private final UserAdaptor userAdaptor;
  private final UserValidator userValidator;
  
  public UserProfileVo execute(Long neighborId) {
    Long userId = SecurityUtils.getCurrentUserId();
    userValidator.isNeighbor(userId, neighborId);
    User neighbor = userAdaptor.query(neighborId);
    return neighbor.toUserProfileVo();
  }
}
