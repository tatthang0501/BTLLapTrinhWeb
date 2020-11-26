package ptit.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ptit.MonthlyTicket;

public interface MonthlyTicketRepository extends CrudRepository<MonthlyTicket,Integer>{
    @Query(value="SELECT * FROM tblmonthly WHERE tblmonthly.xeid =:xeid",nativeQuery=true)
    public MonthlyTicket findByMotoId(@Param("xeid") int xeid);
}
