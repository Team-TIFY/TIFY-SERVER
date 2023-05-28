package tify.server.api.user.service;


import tify.server.api.user.model.response.UserInfoResponse;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UserInfoUseCase {

    private final UserUtils userUtils;

    public UserInfoResponse execute() {
        return UserInfoResponse.from(userUtils.getUser());
    }
}
