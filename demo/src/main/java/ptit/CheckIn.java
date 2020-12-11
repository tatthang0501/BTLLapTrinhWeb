package ptit;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="tblcheckin")
@SecondaryTable(name = "tblcheckout", pkJoinColumns = @PrimaryKeyJoinColumn(name = "checkin"))
public class CheckIn {
    @Id
    private int id;
    private Date time;
    @OneToOne
    @JoinColumn(name = "motorBikeid")
    private Motorbike motorbike;


    @PrePersist
    void thoigian(){
        time = new Date();
    }
}
