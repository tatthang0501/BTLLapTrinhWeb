package ptit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name="tblmonthly")
public class MonthlyTicket {
    @Id
    private int id;
    @NotNull
    private Student student;
    @NotNull
    private int thang;
    @NotNull
    private Motorbike motorbike;
}
