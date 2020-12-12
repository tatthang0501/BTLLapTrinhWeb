package ptit.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ptit.CheckOut;

public interface CheckOutRepository extends CrudRepository<CheckOut, Integer>{
    
    @Query(value="select * from tblcheckout where tblcheckout.checkinid = ?1",nativeQuery = true)
	List<CheckOut> findCheckOut(int id);
}
