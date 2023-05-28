package tify.server.api.auth.model.request;


import tify.server.domain.domains.user.domain.Profile;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty private String email;
    private String phoneNumber;
    private String profileImage;
    @NotEmpty private String name;

    public Profile toProfile() {
        return Profile.builder().thumbNail(this.profileImage).userName(name).email(email).build();
    }
}
