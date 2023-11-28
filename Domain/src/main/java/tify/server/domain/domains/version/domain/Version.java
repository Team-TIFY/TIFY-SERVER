package tify.server.domain.domains.version.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Setter
@Entity
@Table(name = "tbl_version")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Version extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private String iosVersion;

    @NotNull private String aosVersion;

    @Builder
    public Version(String iosVersion, String aosVersion) {
        this.iosVersion = iosVersion;
        this.aosVersion = aosVersion;
    }
}
