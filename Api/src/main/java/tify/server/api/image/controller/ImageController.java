package tify.server.api.image.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.image.model.request.PreSignedUrlRequest;
import tify.server.infrastructure.outer.s3.S3Service;
import tify.server.infrastructure.outer.s3.dto.PreSignedDTO;

@RestController
@RequiredArgsConstructor
@Tag(name = "8. [이미지]")
@RequestMapping(value = "/images")
public class ImageController {

    private final S3Service s3Service;

    @PostMapping
    public PreSignedDTO getPreSignedUrl(@RequestBody @Valid PreSignedUrlRequest request) {
        return s3Service.getPreSignedUrl(
                SecurityUtils.getCurrentUserId(), request.getFileExtension());
    }
}
