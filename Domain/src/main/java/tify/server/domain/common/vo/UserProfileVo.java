package tify.server.domain.common.vo;

import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.User;

@Getter
@Builder
public class UserProfileVo {
  
  private final String userName;
  
  private final String email;
  
  private final String thumbnail;
  
  private final String birth;
  
  private final String job;
  
  public static UserProfileVo from(User user) {
    return UserProfileVo.builder()
            .userName(user.getProfile().getUserName())
            .email(user.getProfile().getEmail())
            .thumbnail(user.getProfile().getThumbNail())
            .birth(user.getProfile().getBirth())
            .job(user.getProfile().getJob())
            .build();
  }
  
}
