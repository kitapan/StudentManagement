package raisetech.StudentManagement.service;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> serchStudentList() {
    return repository.search();
  }

  public StudentDetail searchStudent(String id){
    Student student = repository.searchStudent(id);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
  }

//  第11回演習課題　年齢が30代の人のみを抽出し、コントローラーに渡しましょう
//  public List<Student> serchStudentList() {
//    return repository.search().stream()
//        .filter(student -> student.getAge() >= 25)
//        .collect(Collectors.toList());
//  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchStudentsCoursesList();
  }

//  第11回演習課題　「Javaコース」のコース情報のみを抽出して、コントローラーに渡しましょう
//  public List<StudentsCourses> searchStudentsCoursesList() {
//    return repository.searchStudentsCourses().stream()
//        .filter(course -> "Java".equals(course.getCourseName()))
//        .collect(Collectors.toList());
//  }
  @Transactional
  public void registerStudent(StudentDetail studentDetail){
    repository.registerStudent(studentDetail.getStudent());
    for(StudentsCourses studentsCourses : studentDetail.getStudentsCourses()){
      studentsCourses.setStudentId(studentDetail.getStudent().getId());
      studentsCourses.setCourseStartAt(LocalDateTime.now());
      studentsCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));
      repository.registerStutensCourses(studentsCourses);
    }
  }
  @Transactional
  public void updateStudent(StudentDetail studentDetail){
    repository.updateStudent(studentDetail.getStudent());
    for(StudentsCourses studentsCourse : studentDetail.getStudentsCourses()){
      repository.updateStudentsCourses(studentsCourse);
    }
  }
}
