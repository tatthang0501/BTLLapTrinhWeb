package ptit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="tblcheckin")
public class CheckIn {
    @Id
    private int id;
    private Date thoigian;
    private Motorbike motorbike;

    @PrePersist
    void thoigian(){
        thoigian = new Date();
    }
}
