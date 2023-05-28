package tify.server.api.utils;


import tify.server.api.config.security.SecurityUtils;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserAdaptor userAdaptor;

    public Long getUserId() {
        return SecurityUtils.getCurrentUserId();
    }

    public User getUser() {
        return userAdaptor.query(SecurityUtils.getCurrentUserId());
    }
}
