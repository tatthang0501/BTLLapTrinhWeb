package ptit.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ptit.Motorbike;

public interface MotorbikeRepository extends CrudRepository<Motorbike,Integer>{
    public Iterable<Motorbike> findBybienso(String bienso);

}
