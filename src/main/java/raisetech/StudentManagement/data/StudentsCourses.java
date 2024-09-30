package raisetech.StudentManagement.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

  public String id;
  public String studentId;
  public String courseName;
  public LocalDateTime courseStartAt;
  public LocalDateTime courseEndAt;
}
