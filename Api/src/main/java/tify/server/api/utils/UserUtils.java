package tify.server.api.utils;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tify.server.api.config.security.SecurityUtils;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserTagAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserTag;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserAdaptor userAdaptor;
    private final UserTagAdaptor userTagAdaptor;

    private Long UserTagId = 1L;

    public Long getUserId() {
        return SecurityUtils.getCurrentUserId();
    }

    public User getUser() {
        return userAdaptor.query(SecurityUtils.getCurrentUserId());
    }

    //    public Long getUserTagId() {
    //        return ??
    //    }

    public UserTag getUserTag() {
        return userTagAdaptor.query(UserTagId);
    }
}
