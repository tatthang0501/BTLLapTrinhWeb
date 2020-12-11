package ptit.data;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ptit.MonthlyTicket;
import ptit.Motorbike;
@Repository
public interface MonthlyTicketRepository extends CrudRepository<MonthlyTicket,Integer>{
    public MonthlyTicket findBymotorbike(Motorbike motorbike);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM tblmonthlyticket WHERE tblmonthlyticket.studentid = ?1",nativeQuery=true)
    void deleteByStudentId(int sinhvienid);
}
