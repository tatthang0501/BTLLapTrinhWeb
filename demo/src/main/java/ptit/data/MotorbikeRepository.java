package ptit.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ptit.Motorbike;

public interface MotorbikeRepository extends CrudRepository<Motorbike,Integer>{
    @Query(value="SELECT * FROM tblxe WHERE tblxe.bienso LIKE %:bienso%",nativeQuery=true)
    public Iterable<Motorbike> findByLicense(@Param("bienso") String bienso);

}
