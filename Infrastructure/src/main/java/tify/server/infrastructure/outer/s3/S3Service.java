package tify.server.infrastructure.outer.s3;

import static com.amazonaws.HttpMethod.PUT;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tify.server.core.consts.FileExtension;
import tify.server.core.properties.S3Properties;
import tify.server.infrastructure.exception.FeignException;
import tify.server.infrastructure.outer.s3.dto.PreSignedDTO;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3Client;

    private final S3Properties s3Properties;

    public PreSignedDTO getPreSignedUrl(Long userId, FileExtension fileExtension) {
        String uuidString = UUID.randomUUID().toString();
        String fileName = userId + "-" + uuidString + fileExtension.getValue();
        return generatePreSignedUrl(fileName);
    }

    private PreSignedDTO generatePreSignedUrl(String fileName) {
        Date date = new Date();
        long time = date.getTime();
        time += 1000 * 60 * 30;
        date.setTime(time);
        String bucket = s3Properties.getBucketName();
        try {
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucket, fileName)
                            .withMethod(PUT)
                            .withExpiration(date);
            generatePresignedUrlRequest.addRequestParameter(
                    Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());
            URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
            return new PreSignedDTO(url.toString(), fileName);
        } catch (NullPointerException e) {
            throw FeignException.EXCEPTION;
        }
    }
}
