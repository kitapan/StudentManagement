package raisetech.StudentManagement.service;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository,StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です。全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細の一覧（全件）
   */
  public List<StudentDetail> serchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourse> studentsCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList, studentsCourseList);
  }

  /**
   * 受講生詳細検索です。IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  public StudentDetail searchStudent(String id){
    Student student = repository.searchStudent(id);
    List<StudentsCourse> studentCourses = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourses);
  }

//  第11回演習課題　年齢が30代の人のみを抽出し、コントローラーに渡しましょう
//  public List<Student> serchStudentList() {
//    return repository.search().stream()
//        .filter(student -> student.getAge() >= 25)
//        .collect(Collectors.toList());
//  }

//  public List<StudentsCourses> searchStudentsCoursesList() {
//    return repository.searchStudentsCoursesList();
//  }

//  第11回演習課題　「Javaコース」のコース情報のみを抽出して、コントローラーに渡しましょう
//  public List<StudentsCourses> searchStudentsCoursesList() {
//    return repository.searchStudentsCourses().stream()
//        .filter(course -> "Java".equals(course.getCourseName()))
//        .collect(Collectors.toList());
//  }

  /**
   * 受講生詳細の登録を行います。
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail){
    Student student = studentDetail.getStudent();
    
    repository.registerStudent(student);
    studentDetail.getStudentCourseList().forEach(studentsCourses -> {
      initStudentsCourse(studentsCourses, student.getId());
      repository.registerStudentCourse(studentsCourses);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentsCourses 受講生コース情報
   * @param id 受講生ID
   */
  void initStudentsCourse(StudentsCourse studentsCourses, String id) {
    LocalDateTime now = LocalDateTime.now();

    studentsCourses.setStudentId(id);
    studentsCourses.setCourseStartAt(now);
    studentsCourses.setCourseEndAt(now.plusYears(1));
  }

  /**
   * 受講生詳細の更新を行います。
   * 受講生と受講生コース情報をそれぞれ更新します。
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail){
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
  }
}
