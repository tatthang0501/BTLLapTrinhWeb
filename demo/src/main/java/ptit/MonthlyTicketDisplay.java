package ptit;

import lombok.Data;

@Data
public class MonthlyTicketDisplay {
    private int id;
    private Student student;
    private int month;
    private Motorbike motorbike;
}
