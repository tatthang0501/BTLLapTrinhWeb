package ptit.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ptit.Student;

public interface StudentRepository extends CrudRepository<Student,Integer>{
    @Query(value = "SELECT * FROM tbl_student WHERE (NOT EXISTS (SELECT id FROM tblmonthly WHERE tblmonthly.sinhvienid = tbl_student.id))",nativeQuery = true)
    public List<Student> findStudentNotHavedTicketYet();
}
