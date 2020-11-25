package ptit.data;

import org.springframework.data.repository.CrudRepository;

import ptit.Student;

public interface StudentRepository extends CrudRepository<Student,Integer>{
}
