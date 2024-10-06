package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentsCourse {

  public String id;
  public String studentId;
  public String courseName;
  public LocalDateTime courseStartAt;
  public LocalDateTime courseEndAt;
}
