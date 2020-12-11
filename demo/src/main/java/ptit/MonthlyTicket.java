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
@Table(name="tblmonthlyticket")
public class MonthlyTicket {
    @Id
    private int id;
    @NotNull
    @OneToOne
    @JoinColumn(name = "studentid")
    private Student student;
    @NotNull
    private int month;

    @NotNull
    @OneToOne
    @JoinColumn(name = "motorbikeid")
    private Motorbike motorbike;
}
