package ptit.data;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ptit.CheckIn;

public interface CheckInRepository extends CrudRepository<CheckIn,Integer>{
    @Query(value="select * from tblcheckin where DATE(tblcheckin.time) = ?1 AND tblcheckin.motorbikeid ?2",nativeQuery = true)
    Iterable<CheckIn> findCheckInToday(Date date, int id);
}
