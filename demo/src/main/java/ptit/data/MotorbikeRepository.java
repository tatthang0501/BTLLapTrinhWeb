package ptit.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ptit.Motorbike;

public interface MotorbikeRepository extends CrudRepository<Motorbike,Integer>{
    public Iterable<Motorbike> findByLicensePlates(String bienso);

    @Query(value="SELECT * FROM tblmotorbike WHERE tblmotorbike.licensePlates = ?1",nativeQuery = true)
    public Motorbike findOneByeMotorId(String bienso);


    @Query(value = "SELECT * FROM tblmotorbike WHERE (NOT EXISTS (SELECT id FROM tblmonthlyticket WHERE tblmonthlyticket.motorbikeid = tblmotorbike.id))",nativeQuery = true)
    public List<Motorbike> findMotorbikeNotHavedTicketYet();


    @Transactional
    @Modifying
    @Query(value="DELETE FROM tblmotorbike WHERE tblmotorbike.studentid = ?1",nativeQuery=true)
    void deleteByStudentId(int sinhvienid);
}
