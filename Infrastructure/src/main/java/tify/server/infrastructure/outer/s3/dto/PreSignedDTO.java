package tify.server.infrastructure.outer.s3.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PreSignedDTO {

    private String imageUrl;

    private String name;
}
