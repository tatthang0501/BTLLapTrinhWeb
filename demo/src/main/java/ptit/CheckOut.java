package ptit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class CheckOut {
    @Id
    private int id;
    @NotNull
    private Date thoigian;
    @NotNull
    private CheckIn checkIn;

    @PrePersist
    void thoigian(){
        this.thoigian = new Date();
    }
}
