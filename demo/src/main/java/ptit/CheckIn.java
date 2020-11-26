package ptit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class CheckIn {
    @Id
    private int id;
    @NotNull
    private Date thoigian;
    @NotNull
    private int xeid;

    @PrePersist
    void thoigian(){
        this.thoigian = new Date();
    }
}
