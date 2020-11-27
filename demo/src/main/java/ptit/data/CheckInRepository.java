package ptit.data;

import org.springframework.data.repository.CrudRepository;

import ptit.CheckIn;

public interface CheckInRepository extends CrudRepository<CheckIn,Integer>{
    
}
