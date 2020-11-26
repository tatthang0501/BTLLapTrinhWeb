package ptit.data;

import org.springframework.data.repository.CrudRepository;

import ptit.MonthlyTicket;

public interface MonthlyTicketRepository extends CrudRepository<MonthlyTicket,Integer>{
    
}
