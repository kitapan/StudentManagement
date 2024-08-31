package raisetech.StudentManagement.service;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
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

//  第11回演習課題　年齢が30代の人のみを抽出し、コントローラーに渡しましょう
//  public List<Student> serchStudentList() {
//    return repository.search().stream()
//        .filter(student -> student.getAge() >= 25)
//        .collect(Collectors.toList());
//  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchStudentsCourses();
  }

//  第11回演習課題　「Javaコース」のコース情報のみを抽出して、コントローラーに渡しましょう
//  public List<StudentsCourses> searchStudentsCoursesList() {
//    return repository.searchStudentsCourses().stream()
//        .filter(course -> "Java".equals(course.getCourseName()))
//        .collect(Collectors.toList());
//  }
}
