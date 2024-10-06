package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourse;
import raisetech.StudentManagement.domain.StudentDetail;

/**
 * 受講生書斎を受講生や受講生コース情報、もしくわその逆の変換を行くコンバーターです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コースをマッピングする。
   * 受講生コース情報は受講生に対して複数存在するのでルールを回して受講生詳細情報を組み立てる。
   * @param studentList 受講生一覧
   * @param studentsCourseList 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentsCourse> studentsCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentsCourse> convertStudentCourseList = studentsCourseList.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());
      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
