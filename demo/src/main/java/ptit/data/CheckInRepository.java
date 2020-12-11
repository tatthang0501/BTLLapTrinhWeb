package ptit.data;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ptit.CheckIn;

public interface CheckInRepository extends CrudRepository<CheckIn,Integer>{
    @Query(value="select * from tblcheckin where DATE(tblcheckin.time) =:date AND tblcheckin.motorbikeid =:id",nativeQuery = true)
    Iterable<CheckIn> findCheckInToday(@Param("date") String date, @Param("id") int id);
}
