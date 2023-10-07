package tify.server.domain.domains;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class AbstractTimeStamp {

    @Column(nullable = false)
    @CreationTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd hh:mm:ss",
            timezone = "Asia/Seoul")
    private Timestamp createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd hh:mm:ss",
            timezone = "Asia/Seoul")
    private Timestamp updatedAt;

    public void setUpdatedAt() {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
