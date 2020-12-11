package ptit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name="tblcheckout")
public class CheckOut {
    @Id
    private int id;
    @NotNull
    private Date thoigian;
    @NotNull
    @OneToOne
    @JoinColumn(name = "checkin")
    private CheckIn checkin;

    @PrePersist
    void thoigian(){
        this.thoigian = new Date();
    }
}
