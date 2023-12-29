package tify.server.core.consts;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileExtension {
    PNG(".png"),
    JPG(".jpg"),
    JPEG(".jpeg"),
    ;

    final String value;
}
