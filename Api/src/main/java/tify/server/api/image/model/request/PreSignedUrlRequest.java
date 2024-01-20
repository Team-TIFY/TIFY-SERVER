package tify.server.api.image.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.core.consts.FileExtension;

@Getter
@NoArgsConstructor
public class PreSignedUrlRequest {

    @Schema(description = "파일의 확장자입니다.", implementation = FileExtension.class)
    @NotNull(message = "파일의 확장자를 입력하세요.")
    private FileExtension fileExtension;
}
