package ptit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
    @OneToOne
    @JoinColumn(name = "sinhvienid")
    private Student student;
    @NotNull
    private int thang;
    
    @NotNull
    @OneToOne
    @JoinColumn(name = "xeid")
    private Motorbike motorbike;
}
